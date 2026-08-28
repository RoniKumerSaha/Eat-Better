package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.model.FoodItem

/**
 * Pick an icon for a food.
 *
 * Resolution order (first match wins):
 *  1. Specific food-name keyword match (apple → Spa, biryani → RiceBowl, etc.)
 *  2. The food's stored `iconSymbol` string (legacy / explicit overrides)
 *  3. Category fallback
 *
 * This is intentionally UI-layer logic: it lets us evolve the mapping without
 * Room migrations on the seed data.
 */
fun getFoodIcon(food: FoodItem): ImageVector {
  val n = food.name.lowercase()

  // ---- 1. Name keyword match -------------------------------------------
  when {
    // Fruits (Material has no Apple/Mango/etc. → Spa = leaf/plant glyph)
    n.contains("apple") -> return Icons.Default.Spa
    n.contains("mango") -> return Icons.Default.Spa
    n.contains("banana") -> return Icons.Default.Spa
    n.contains("orange") || n.contains("malta") -> return Icons.Default.Spa
    n.contains("lemon") || n.contains("lime") -> return Icons.Default.Spa
    n.contains("watermelon") || n.contains("tormuj") -> return Icons.Default.Spa
    n.contains("guava") || n.contains("peyara") -> return Icons.Default.Spa
    n.contains("papaya") || n.contains("pepe") -> return Icons.Default.Spa
    n.contains("dragon fruit") || n.contains("pitaya") -> return Icons.Default.Spa
    n.contains("avocado") -> return Icons.Default.Spa
    n.contains("strawberr") || n.contains("blueberr") -> return Icons.Default.Spa
    n.contains("jamun") || n.contains("blackberry") -> return Icons.Default.Spa

    // Salads / raw greens → Eco (leaf)
    n.contains("salad") -> return Icons.Default.Eco

    // Drinks: cup (LocalDrink) vs LocalCafe for hot beverages
    n.contains("coffee") || n.contains("americano") -> return Icons.Default.LocalCafe
    n.contains("matcha") || n.contains("tea") -> return Icons.Default.LocalCafe
    n.contains("cha") -> return Icons.Default.LocalCafe // "cha" = tea in Bengali
    n.contains("horlicks") -> return Icons.Default.LocalCafe
    n.contains("haldi") || n.contains("turmeric milk") -> return Icons.Default.LocalCafe
    n.contains("doodh") -> return Icons.Default.LocalCafe // milk

    n.contains("juice") -> return Icons.Default.LocalDrink
    n.contains("water") -> return Icons.Default.WaterDrop
    n.contains("coconut") || n.contains("daab") -> return Icons.Default.LocalDrink
    n.contains("sugarcane") -> return Icons.Default.LocalDrink
    n.contains("cola") || n.contains("soft drink") -> return Icons.Default.LocalDrink
    n.contains("lassi") || n.contains("borhani") -> return Icons.Default.LocalDrink
    n.contains("lemon water") -> return Icons.Default.LocalDrink

    n.contains("liquor") || n.contains("whisky") || n.contains("beer") ||
      n.contains("wine") -> return Icons.Default.Liquor

    // Rice-based meals
    n.contains("biryani") || n.contains("kacchi") -> return Icons.Default.RiceBowl
    n.contains("khichuri") || n.contains("pulao") -> return Icons.Default.RiceBowl
    n.contains("rice") || n.contains("bhat") -> return Icons.Default.RiceBowl

    // Eggs
    n.contains("egg") || n.contains("dim ") || n.contains("omelet") ||
      n.contains("omelette") || n.contains("bhurji") || n.contains("bhaji") && food.categoryId == "snacks" -> return Icons.Default.Egg

    // Fish / seafood (no Fish icon in Material → SetMeal)
    n.contains("fish") || n.contains("mach") || n.contains("ilish") ||
      n.contains("bhetki") || n.contains("tilapia") || n.contains("salmon") ||
      n.contains("chingri") || n.contains("prawn") || n.contains("shrimp") ||
      n.contains("rohu") || n.contains("hilsa") -> return Icons.Default.SetMeal

    // Soups / lentil / mash
    n.contains("soup") -> return Icons.Default.SoupKitchen
    n.contains("dal") || n.contains("daal") || n.contains("stew") -> return Icons.Default.SoupKitchen
    n.contains("jhol") || n.contains("curry") -> return Icons.Default.SoupKitchen
    n.contains("vorta") || n.contains("bhorta") -> return Icons.Default.SoupKitchen // mashed

    // Junk / sweets / snacks-as-meal → burger
    n.contains("burger") || n.contains("cheeseburger") -> return Icons.Default.Fastfood
    n.contains("pizza") -> return Icons.Default.Fastfood
    n.contains("fries") -> return Icons.Default.Fastfood
    n.contains("fried chicken") -> return Icons.Default.Fastfood
    n.contains("chips") || n.contains("crisp") || n.contains("potato crisps") -> return Icons.Default.Fastfood
    n.contains("noodles") || n.contains("instant noodle") -> return Icons.Default.Fastfood
    n.contains("samosa") || n.contains("shingara") || n.contains("singara") -> return Icons.Default.Fastfood
    n.contains("spring roll") || n.contains("beguni") -> return Icons.Default.Fastfood
    n.contains("fuchka") || n.contains("pani puri") || n.contains("chotpoti") -> return Icons.Default.Fastfood
    n.contains("chocolate") -> return Icons.Default.Fastfood
    n.contains("biscuit") || n.contains("toast") -> return Icons.Default.Fastfood
    n.contains("cake") || n.contains("sandesh") || n.contains("roshogolla") ||
      n.contains("misti") -> return Icons.Default.Fastfood
    n.contains("yogurt") || n.contains("doi") -> return Icons.Default.Fastfood
    n.contains("chaat") || n.contains("jhalmuri") -> return Icons.Default.Fastfood
  }

  // ---- 2. Stored iconSymbol (explicit override) -----------------------
  when (food.iconSymbol) {
    "apple", "spa" -> return Icons.Default.Spa
    "eco" -> return Icons.Default.Eco
    "water_drop" -> return Icons.Default.WaterDrop
    "egg" -> return Icons.Default.Egg
    "rice_bowl" -> return Icons.Default.RiceBowl
    "soup_kitchen" -> return Icons.Default.SoupKitchen
    "coffee", "tea" -> return Icons.Default.LocalCafe
    "liquor" -> return Icons.Default.Liquor
    "lunch_dining" -> return Icons.Default.LunchDining
    "set_meal" -> return Icons.Default.SetMeal
    "restaurant" -> return Icons.Default.Restaurant
    "nutrition" -> return Icons.Default.Restaurant
  }

  // ---- 3. Category fallback -------------------------------------------
  return when (food.categoryId) {
    "vegetables" -> Icons.Default.Eco           // leaf
    "meals" -> Icons.Default.LunchDining         // meal / dining
    "snacks" -> Icons.Default.Fastfood            // burger
    "drinks" -> Icons.Default.LocalDrink          // cup
    "fruits" -> Icons.Default.Spa                 // fruit/plant
    else -> Icons.Default.Restaurant
  }
}