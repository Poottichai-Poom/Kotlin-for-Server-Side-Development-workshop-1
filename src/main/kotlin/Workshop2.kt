package org.example

// 1. กำหนด data class สำหรับเก็บข้อมูลสินค้า
data class Product(val name: String, val price: Double, val category: String)

fun main() {
    val products = listOf(
        Product("Laptop", 35000.0, "Electronics"),
        Product("Smartphone", 25000.0, "Electronics"),
        Product("T-shirt", 450.0, "Apparel"),
        Product("Monitor", 7500.0, "Electronics"),
        Product("Keyboard", 499.0, "Electronics"),
        Product("Jeans", 1200.0, "Apparel"),
        Product("Headphones", 1800.0, "Electronics"),
    )
    println("รายการสินค้าทั้งหมด:")
    products.forEach {
        println(it)
    }
        println("--------------------------------------------------")

//         --- โจทย์: จงหาผลรวมราคาสินค้าทั้งหมดในหมวด 'Electronics' ที่มีราคามากกว่า 500 บาท ---
//         3. วิธีที่ 1: การใช้ Chaining กับ List โดยตรง
//         กรองสินค้าหมวด Electronics
//         กรองสินค้าที่ราคามากกว่า 500
//         ดึงเฉพาะราคาออกมาเป็น List<Double>
//         หาผลรวมของราคา
    val totalElecPriceOver500 = products
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .fold(0.0) { acc, it -> acc + it }

        println("วิธีที่ 1: ใช้ Chaining กับ List")
        println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500 บาท")
        println("--------------------------------------------------")

    val totalElecPriceOver500Sequence =  products
        .asSequence()
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .fold(0.0) { acc, it -> acc + it }

    println("วิธีที่ 2: ใช้ .asSequence() (ขั้นสูง)")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500Sequence บาท")
    println("--------------------------------------------------")


    val grouped = products.groupBy { product ->
        when {
            product.price <= 1000 -> "ไม่เกิน 1,000 บาท"
            product.price <= 9999 -> "1,000 - 9,999 บาท"
            else -> "10,000 บาทขึ้นไป"
        }
    }

    grouped.forEach { (range, items) ->
        println("กลุ่ม: $range")
        items.forEach { println("- ${it.name} (${it.price})") }
        println()
    }



    println("วิธีที่ 2: ใช้ .asSequence() (ขั้นสูง)")
    println("ผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท: $totalElecPriceOver500Sequence บาท")
    println("--------------------------------------------------")

    println("อภิปรายความแตกต่างระหว่าง List และ Sequence:")
        println("1. List Operations (วิธีที่ 1):")
        println("   - ทุกครั้งที่เรียกใช้ operation (เช่น filter, map) จะมีการสร้าง Collection (List) ใหม่ขึ้นมาเพื่อเก็บผลลัพธ์ของขั้นนั้นๆ")
        println("   - ตัวอย่าง: filter ครั้งแรกสร้าง List ใหม่ -> filter ครั้งที่สองสร้าง List ใหม่อีกใบ -> map สร้าง List สุดท้าย -> sum ทำงาน")
        println("   - เหมาะกับข้อมูลขนาดเล็ก เพราะเข้าใจง่าย แต่ถ้าข้อมูลมีขนาดใหญ่มาก (ล้าน records) จะสิ้นเปลืองหน่วยความจำและเวลาในการสร้าง Collection ใหม่ๆ ซ้ำซ้อน")
        println()
        println("2. Sequence Operations (วิธีที่ 2):")
        println("   - ใช้การประมวลผลแบบ 'Lazy' (ทำเมื่อต้องการใช้ผลลัพธ์จริงๆ)")
        println("   - operations ทั้งหมด (filter, map) จะไม่ทำงานทันที แต่จะถูกเรียงต่อกันไว้")
        println("   - ข้อมูลแต่ละชิ้น (each element) จะไหลผ่าน Pipeline ทั้งหมดทีละชิ้น จนจบกระบวนการ")
        println("   - เช่น: 'Laptop' จะถูก filter category -> filter price -> map price จากนั้น 'Smartphone' ถึงจะเริ่มทำกระบวนการเดียวกัน")
        println("   - จะไม่มีการสร้าง Collection กลางทาง ทำให้ประหยัดหน่วยความจำและเร็วกว่ามากสำหรับชุดข้อมูลขนาดใหญ่ เพราะทำงานกับข้อมูลทีละชิ้นและทำทุกขั้นตอนให้เสร็จในรอบเดียว")
        println("   - การคำนวณจะเกิดขึ้นเมื่อมี 'Terminal Operation' มาเรียกใช้เท่านั้น (ในที่นี้คือ .sum())")
    }


fun totalElecPriceOver500Sequences(products: List<Product>): Double {
    val totalElecPriceOver500s =  products
        .asSequence()
        .filter { it.category == "Electronics" }
        .filter { it.price > 500.0 }
        .map { it.price }
        .fold(0.0) { acc, it -> acc + it }
    return totalElecPriceOver500s
}

fun totalElecPriceOver500(products: List<Product>): Int {
    val totalElecPriceOver500s =  products
        .filter { it.category == "Electronics" && it.price > 500.0 }
        .size
    return totalElecPriceOver500s
}
