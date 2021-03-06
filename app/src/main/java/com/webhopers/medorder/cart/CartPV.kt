package com.webhopers.medorder.cart

import com.webhopers.medorder.interfaces.Presenter
import com.webhopers.medorder.interfaces.View
import com.webhopers.medorder.models.OrderResponse
import com.webhopers.medorder.models.ProductF
import com.webhopers.medorder.services.firebase.FirebaseDatabaseService
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.MakeOrder
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartPresenter(val view: CartView): Presenter {

    val disposable = CompositeDisposable()

    init {
        view.enablePlaceOrderButton(false)
        isCartEmpty()
    }

    fun isCartEmpty() {
        view.showProgressBar(true)
        disposable.add(FirebaseDatabaseService.cartListener("user_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            view.showProgressBar(false)
                        },
                        onComplete = {
                            view.showProgressBar(false)
                        },
                        onNext = {
                            view.showProgressBar(false)
                            if (it)  view.enablePlaceOrderButton(false)
                            else view.enablePlaceOrderButton(true)
                        }))
    }

    fun onPlaceOrderClick() {
        view.showProgressBar(true)
        disposable.add(FirebaseDatabaseService.getCartItems("user_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<ProductF>?>() {
                    override fun onError(e: Throwable) {
                        view.showProgressBar(false)
                        view.makeToast("Network Error")
                    }

                    override fun onSuccess(list: ArrayList<ProductF>?) {
                        if (list != null) {
                            WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                                    .createOrder(MakeOrder(list).done())
                                    .enqueue(object : Callback<OrderResponse> {
                                        override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                                            view.showProgressBar(false)
                                            if (response.isSuccessful) {
                                                view.makeToast("Order Placed")
                                                FirebaseDatabaseService.emptyCart("user_id")
                                            } else { view.makeToast("Network Error")}
                                        }

                                        override fun onFailure(call: Call<OrderResponse>, t: Throwable?) {
                                            view.showProgressBar(false)
                                            view.makeToast("Network Error")
                                        }
                                    })
                        }
                    }
                }))

    }

    override fun stop() {
        disposable.clear()
    }



}

interface CartView: View {
    fun makeToast(message: String)
    fun enablePlaceOrderButton(bool: Boolean)
}