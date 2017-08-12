package com.webhopers.medorder.fake.fakeServices

import com.webhopers.medorder.models.Product
import java.util.*

object ProductService {

    private val productNames = arrayListOf<String>(
            "Acesoft-MR",
            "Artifix",
            "Calmunch",
            "Cefvent-S 1.5",
            "Contimax",
            "Candiwiz-150",
            "Dailyrich",
            "Defmune 6",
            "Gored XT",
            "Glimisoft Mi",
            "Gut Ultra",
            "Grimtus CS",
            "Histamax",
            "Histamune-M",
            "Mefpure-P",
            "Neuroxia",
            "Nixim CV",
            "Nudec 25",
            "Trupod 100 DT",
            "Telmisoft 40",
            "ZGO"
    )

    private val productPrices = arrayListOf<String>(
            "100",
            "1250",
            "49",
            "34.50",
            "499",
            "60",
            "72",
            "108",
            "199",
            "25",
            "300",
            "40"
    )

    private val productQuantities = arrayListOf<String>(
            "54",
            "12",
            "98",
            "143",
            "432",
            "55",
            "2",
            "0",
            "85",
            "36",
            "123",
            "78",
            "235",
            "97"
    )

    private val productDesc = arrayListOf<String>(
            "Aceclofenac 100mg+Paracetamol 325mg ",
            "Artemether 80 mg and lumefantrine 480 mg",
            "Calcitriol 0.25mcg + Calcium Citrate 750 mg + Zinc Sulphate Monohydrate7.5mg + Magnesium Oxide 100mg",
            "Lycopene 10% 6000 mcg+Vitamin A 5000 I.U+Vitamin C 150mg+Vitamin E 15 I.U+Selenium dioxide monohydrate eq. to Selenium 75mcg+Zinc Sulphate Monohydrate 27.45 mg+Vitamin B-12 5mcg+Pyridoxim HCL1.5mg+Folic Acid 1.5mg+Manganese eq. to manganese sulphate 2 mg+Copper as copper Sulphate 1mg+Chromium as chromium chloride100mcg+Potassium as potassium Iodide 50mcg.",
            "Ferrous Ascorbate 100mg +L-Methylfolate 1mg, Pyridoxal 5 phosphate1.5mg, methylcobalamin 1500mcg, zinc 22.5mg",
            "Chondroitin sulfate 50 MG+Collagen-Peptide C-2 150 MG+Elemental copper 0.5 MG+Elemental iron 5 MG+Elemental manganese 2 MG+Elemental selenium 60 MCG+Elemental zinc 5 MG+Folic acid 400 MCG+Glucosamine 750 MG+Vitamin B12 10 MCG+Vitamin C 30 MG+Vitamin D3 25 MCG+Vitamin E 20 MG",
            "Cholecalciferol 60000 MD SUGAR FREE",
            "Mecobalamine 1500mcg + Alpha Lipoic Acid 100mg,Vit bpyridoxine Hydrochloride 3mg+ Folic Acid 1.5 mg (Alu Alu) zinc sulphate 22.5 mg",
            "Entric coated Rabiprazole sodium & Itopride Hudrochloride Sustained realese Capsule"
            )


    fun getProducts(): List<Product> {
        val rand = Random()
        val products = List<Product>(productNames.size) {
            val product = Product()
            product.name = productNames[it]
            product.price = productPrices[rand.nextInt(productPrices.size)]
            product.quantity = productQuantities[rand.nextInt(productQuantities.size)]
            product.desc = productDesc[rand.nextInt(productDesc.size)]
            product
        }
        return products
    }
}