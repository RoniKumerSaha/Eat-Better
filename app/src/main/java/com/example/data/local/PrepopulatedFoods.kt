package com.example.data.local

import com.example.data.local.entity.FoodEntity
import org.json.JSONArray
import org.json.JSONObject

object PrepopulatedFoods {

  private fun portion(
    id: String,
    name: String,
    multiplier: Float,
    calories: Int,
    carbs: Float,
    protein: Float,
    fat: Float,
    fiber: Float,
    sugar: Float,
    sodium: Float
  ): JSONObject {
    return JSONObject().apply {
      put("id", id)
      put("name", name)
      put("multiplier", multiplier)
      put("calories", calories)
      put("carbs", carbs)
      put("protein", protein)
      put("fat", fat)
      put("fiber", fiber)
      put("sugar", sugar)
      put("sodium", sodium)
    }
  }

  private fun portions(vararg items: JSONObject): String {
    val array = JSONArray()
    items.forEach { array.put(it) }
    return array.toString()
  }

  val list: List<FoodEntity> = listOf(
    // ---------------- FRUITS ----------------
    FoodEntity(
      id = "fuji_apple",
      name = "Fuji Apple",
      bengaliName = "ফুজি আপেল",
      categoryId = "fruits",
      region = "Global",
      baseScore = 9,
      educationalText = "Crisp, sweet, and packed with soluble fiber (pectin) which gently stabilizes blood sugar and supports gut digestion.",
      scoreExplanation = "• Excellent source of dietary fiber\n• Rich in vitamin C and polyphenols\n• Minimally processed natural whole fruit\n• Low glycemic response",
      searchAliases = "apple,fuji,apel,fruit,healthy,snack",
      portionsJson = portions(
        portion("small", "½ Apple (90g)", 0.5f, 48, 12.5f, 0.2f, 0.1f, 2.0f, 9.5f, 1f),
        portion("medium", "1 Medium Apple (180g)", 1.0f, 95, 25.0f, 0.5f, 0.3f, 4.4f, 19.0f, 2f),
        portion("large", "1 Large Apple (240g)", 1.3f, 125, 33.0f, 0.7f, 0.4f, 5.8f, 25.0f, 3f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "hass_avocado",
      name = "Hass Avocado",
      bengaliName = "অ্যাভোকাডো",
      categoryId = "fruits",
      region = "Global",
      baseScore = 10,
      educationalText = "A superfood high in heart-healthy monounsaturated oleic acid, potassium, and rich dietary fiber.",
      scoreExplanation = "• Loaded with monounsaturated healthy fats\n• High potassium (more than bananas)\n• Great prebiotic fiber for gut microbiome\n• Zero refined sugar",
      searchAliases = "avocado,hass,healthy fat,keto",
      portionsJson = portions(
        portion("half", "½ Avocado (100g)", 0.5f, 160, 8.5f, 2.0f, 14.7f, 6.7f, 0.7f, 7f),
        portion("whole", "1 Whole Avocado (200g)", 1.0f, 320, 17.0f, 4.0f, 29.4f, 13.4f, 1.4f, 14f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "banana_shobri",
      name = "Banana (Shobri / Cavendish)",
      bengaliName = "কলা (সবরি / সাগর)",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "A convenient natural energy booster rich in potassium, vitamin B6, and prebiotic resistant starch.",
      scoreExplanation = "• Natural sustained energy with no additives\n• High in potassium and vitamin B6\n• Gentle on the stomach and aids digestion",
      searchAliases = "banana,kola,shobri,sagor kola,champa kola",
      portionsJson = portions(
        portion("half", "½ Banana", 0.5f, 52, 13.5f, 0.6f, 0.2f, 1.5f, 6.0f, 1f),
        portion("one", "1 Medium Banana (118g)", 1.0f, 105, 27.0f, 1.3f, 0.4f, 3.1f, 14.4f, 1f),
        portion("two", "2 Bananas", 1.8f, 210, 54.0f, 2.6f, 0.8f, 6.2f, 28.8f, 2f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "green_coconut_water",
      name = "Green Coconut Water (Daab)",
      bengaliName = "ডাবের পানি",
      categoryId = "drinks",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Nature's pure electrolyte drink, refreshing and hydrating with natural potassium, magnesium, and zero artificial flavors.",
      scoreExplanation = "• 100% natural isotonic hydration\n• Rich in potassium and magnesium\n• Unsweetened, unpasteurized natural refreshment",
      searchAliases = "daab,dab,coconut water,daber pani,water,hydration",
      portionsJson = portions(
        portion("glass", "1 Glass (250ml)", 1.0f, 45, 9.0f, 1.0f, 0.2f, 1.1f, 6.0f, 60f),
        portion("whole_daab", "1 Whole Daab (approx 400ml)", 1.5f, 75, 15.0f, 1.7f, 0.4f, 1.8f, 10.0f, 95f)
      ),
      iconSymbol = "water_drop"
    ),
    FoodEntity(
      id = "guava_peyara",
      name = "Fresh Guava (Peyara)",
      bengaliName = "পেয়ারা",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "One of the most nutrient-dense fruits available locally, boasting 4x the vitamin C of oranges and outstanding dietary fiber.",
      scoreExplanation = "• Exceptional Vitamin C content\n• Very high in dietary fiber (9g per fruit)\n• Low sugar and low glycemic index",
      searchAliases = "guava,peyara,piyara,kacha peyara",
      portionsJson = portions(
        portion("half", "½ Medium Guava", 0.5f, 35, 7.0f, 1.3f, 0.5f, 4.5f, 4.0f, 2f),
        portion("one", "1 Whole Guava (150g)", 1.0f, 68, 14.0f, 2.6f, 1.0f, 8.9f, 8.0f, 3f),
        portion("with_kasundi", "1 Guava sliced w/ bit of salt/kasundi", 1.0f, 72, 14.5f, 2.7f, 1.1f, 8.9f, 8.2f, 120f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "mango_fazli_himsagar",
      name = "Ripe Mango (Himsagar / Langra)",
      bengaliName = "পাকা আম (হিমসাগর / ল্যাংড়া)",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Rich in Vitamin A (beta-carotene) and enzymes that support cellular health, best enjoyed in mindful portions.",
      scoreExplanation = "• Rich in Vitamin A, C, and antioxidants\n• Natural whole fruit sweetness\n• Moderate portion recommended due to natural sugars",
      searchAliases = "mango,aam,paka aam,himsagar,langra,fazli",
      portionsJson = portions(
        portion("slice_bowl", "1 Small Cup Sliced (120g)", 0.8f, 75, 18.0f, 1.0f, 0.5f, 2.0f, 16.0f, 2f),
        portion("one_mango", "1 Medium Mango (200g)", 1.0f, 135, 35.0f, 1.8f, 0.8f, 3.7f, 31.0f, 3f)
      ),
      iconSymbol = "apple"
    ),

    // ---------------- VEGETABLES & SHAK ----------------
    FoodEntity(
      id = "palak_shak",
      name = "Spinach Shak Bhaji (Palong / Lal Shak)",
      bengaliName = "পালং শাক / লাল শাক ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Dark leafy greens cooked lightly with garlic and chili, providing iron, folate, lutein, and vital phytonutrients.",
      scoreExplanation = "• Abundant in bioavailable iron and folate\n• Strong antioxidant and carotenoid profile\n• Low in calories, very supportive of digestion",
      searchAliases = "palak,palong,shak,lal shak,spinach,bhaji",
      portionsJson = portions(
        portion("small_katori", "Small Bowl (1/2 cup)", 0.6f, 45, 3.5f, 2.2f, 2.5f, 2.0f, 0.5f, 110f),
        portion("med_katori", "Medium Serving (1 cup)", 1.0f, 85, 7.0f, 4.2f, 4.8f, 4.0f, 1.0f, 190f),
        portion("generous", "Generous Serving (1.5 cups)", 1.4f, 125, 10.0f, 6.0f, 7.0f, 5.8f, 1.4f, 260f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "begun_bhaji",
      name = "Fried Eggplant (Begun Bhaji)",
      bengaliName = "বেগুন ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Eggplant provides nasunin anthocyanins and dietary fiber; score reflects light oil sautéing.",
      scoreExplanation = "• Good source of antioxidants (nasunin) and fiber\n• Moderated base score due to cooking oil absorption",
      searchAliases = "begun,bhaji,eggplant,brinjal,baigan",
      portionsJson = portions(
        portion("one_slice", "1 Medium Slice (50g)", 0.6f, 65, 4.0f, 0.8f, 5.2f, 1.5f, 2.0f, 80f),
        portion("two_slices", "2 Slices (100g)", 1.0f, 130, 8.0f, 1.6f, 10.4f, 3.0f, 4.0f, 160f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "fresh_broccoli",
      name = "Steamed Broccoli",
      bengaliName = "ব্রোকলি",
      categoryId = "vegetables",
      region = "Global",
      baseScore = 10,
      educationalText = "Cruciferous powerhouse containing sulforaphane, vitamin K, and fiber for optimal cellular health.",
      scoreExplanation = "• Powerful sulforaphane antioxidant\n• Exceptional Vitamin K and C content\n• Very low calorie, supports immune defense",
      searchAliases = "broccoli,greens,steamed,veg",
      portionsJson = portions(
        portion("half_cup", "½ Cup florets (45g)", 0.5f, 16, 3.0f, 1.3f, 0.2f, 1.2f, 0.7f, 15f),
        portion("one_cup", "1 Cup florets (90g)", 1.0f, 31, 6.0f, 2.6f, 0.4f, 2.4f, 1.5f, 30f),
        portion("two_cups", "2 Cups generous bowl", 1.8f, 62, 12.0f, 5.2f, 0.8f, 4.8f, 3.0f, 60f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "mixed_vegetable_labra",
      name = "Mixed Vegetable (Labra / Sobji Curry)",
      bengaliName = "পাঁচমিশালি সবজি / লাবড়া",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "A wholesome medley of pumpkin, papaya, cauliflower, and beans cooked with panch phoron spices.",
      scoreExplanation = "• Diverse micronutrient profile from multiple vegetables\n• High fiber and natural digestive spices\n• Low saturated fat",
      searchAliases = "sobji,labra,panchmishali,vegetables,curry",
      portionsJson = portions(
        portion("small_cup", "1 Small Bowl (150g)", 0.7f, 85, 12.0f, 2.5f, 3.0f, 3.5f, 3.0f, 160f),
        portion("medium_bowl", "1 Medium Bowl (250g)", 1.0f, 140, 20.0f, 4.0f, 5.2f, 6.0f, 5.0f, 260f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "alu_bhorta",
      name = "Mashed Potato (Alu Bhorta)",
      bengaliName = "আলু ভর্তা",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Traditional comforting mashed potatoes infused with mustard oil, fried onions, and roasted dry red chili.",
      scoreExplanation = "• Wholesome potato starch and potassium\n• Moderate base score due to mustard oil and glycemic index\n• Balanced traditional side",
      searchAliases = "alu bhorta,aloo vorta,bhorta,vorta,potato",
      portionsJson = portions(
        portion("small_ball", "Small scoop (1 tbsp / 40g)", 0.5f, 55, 8.0f, 1.0f, 2.2f, 1.0f, 0.5f, 70f),
        portion("regular", "Regular serving (2 tbsp / 80g)", 1.0f, 110, 16.0f, 2.0f, 4.5f, 2.0f, 1.0f, 140f)
      ),
      iconSymbol = "nutrition"
    ),

    // ---------------- MEALS ----------------
    FoodEntity(
      id = "moshur_dal",
      name = "Red Lentil Soup (Moshur Dal)",
      bengaliName = "মসুর ডাল (পাতলা / ঘন)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Plant-based protein staple rich in folate, iron, and slow-burning complex carbohydrates.",
      scoreExplanation = "• High in plant protein and soluble fiber\n• Low glycemic index, promotes satiety\n• Tempering with cumin/garlic aids digestion",
      searchAliases = "dal,daal,moshur dal,lentils,soup",
      portionsJson = portions(
        portion("small_katori", "Small Bowl (1/2 cup)", 0.6f, 90, 14.0f, 6.0f, 1.5f, 4.0f, 1.0f, 120f),
        portion("regular_bowl", "1 Regular Bowl (1 cup / 200ml)", 1.0f, 150, 24.0f, 10.0f, 2.5f, 7.0f, 1.5f, 210f),
        portion("large_bowl", "1 Large Bowl (1.5 cups)", 1.4f, 220, 35.0f, 14.5f, 3.8f, 10.0f, 2.2f, 310f)
      ),
      iconSymbol = "soup_kitchen"
    ),
    FoodEntity(
      id = "steamed_white_rice",
      name = "Steamed Rice (Sada Bhat)",
      bengaliName = "সাদা ভাত",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Everyday staple carbohydrate that pairs well with lean proteins, lentils, and lots of vegetables.",
      scoreExplanation = "• Clean, easily digestible staple energy\n• Moderated base score due to refined glycemic starch\n• Best paired with fiber and protein",
      searchAliases = "rice,bhat,sada bhat,plain rice,steamed rice",
      portionsJson = portions(
        portion("small", "Small serving (1 cup cooked / 150g)", 0.7f, 195, 43.0f, 4.0f, 0.4f, 0.6f, 0.1f, 2f),
        portion("medium", "Medium serving (1.5 cups / 225g)", 1.0f, 290, 64.0f, 6.0f, 0.6f, 0.9f, 0.1f, 3f),
        portion("large", "Large serving (2 cups / 300g)", 1.3f, 390, 86.0f, 8.0f, 0.8f, 1.2f, 0.2f, 4f)
      ),
      iconSymbol = "rice_bowl"
    ),
    FoodEntity(
      id = "khichuri_bhuna",
      name = "Bhuna Khichuri (Rice & Lentil Pot)",
      bengaliName = "ভুনা খিচুড়ি",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "The classic balanced combination of rice and lentils forming a complete amino acid protein profile.",
      scoreExplanation = "• Complete protein pairing (rice + lentils)\n• Hearty warmth and steady sustained energy\n• Moderate score accounting for ghee/oil used",
      searchAliases = "khichuri,khichdi,bhuna khichuri,rice lentils",
      portionsJson = portions(
        portion("small_plate", "Small Plate (200g)", 0.7f, 260, 42.0f, 9.0f, 6.5f, 4.0f, 1.5f, 280f),
        portion("medium_plate", "Medium Plate (320g)", 1.0f, 410, 66.0f, 14.0f, 10.5f, 6.2f, 2.5f, 450f),
        portion("large_plate", "Large Plate (450g)", 1.4f, 580, 93.0f, 20.0f, 15.0f, 8.5f, 3.5f, 620f)
      ),
      iconSymbol = "rice_bowl"
    ),
    FoodEntity(
      id = "rui_mach_jhol",
      name = "Rohu Fish Curry with Veggies (Rui Macher Jhol)",
      bengaliName = "রুই মাছের পাতলা ঝোল",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Freshwater Rohu fish cooked in a light cumin-ginger broth with potatoes and pointed gourd (patol). High quality lean protein.",
      scoreExplanation = "• Rich in omega fatty acids and lean protein\n• Cooked with gentle whole spices and vegetables\n• Very light on saturated fats",
      searchAliases = "mach,rui,fish,curry,macher jhol,rohu",
      portionsJson = portions(
        portion("one_piece_gravy", "1 Fish Piece with Gravy (150g)", 0.8f, 160, 5.0f, 18.0f, 7.0f, 1.5f, 1.0f, 240f),
        portion("two_piece_gravy", "2 Fish Pieces with Gravy (280g)", 1.0f, 290, 9.0f, 32.0f, 13.0f, 2.5f, 1.8f, 420f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "ilish_mach_bhaja_shorshe",
      name = "Hilsa Fish (Ilish Mach / Shorshe Ilish)",
      bengaliName = "ইলিশ মাছ (ভাজা / সর্ষে ইলিশ)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Bangladesh's pride, Hilsa is naturally abundant in heart-healthy Marine Omega-3 fatty acids.",
      scoreExplanation = "• Exceptional Omega-3 fatty acid profile\n• High biological value protein\n• Rich natural fat content — best in moderate portions",
      searchAliases = "ilish,hilsha,hilsa,shorshe ilish,mach,fish",
      portionsJson = portions(
        portion("one_piece", "1 Piece Shorshe Ilish (120g)", 1.0f, 240, 2.5f, 22.0f, 16.0f, 1.0f, 0.5f, 290f),
        portion("fried_piece", "1 Fried Ilish Piece (100g)", 0.9f, 220, 1.0f, 20.0f, 15.0f, 0.2f, 0.1f, 180f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "atta_ruti",
      name = "Whole Wheat Roti (Atta Ruti)",
      bengaliName = "আটার রুটি (হাতে বেলা)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Traditional dry-roasted unleavened whole wheat flatbread, free of refined flour and rich in complex fiber.",
      scoreExplanation = "• 100% whole grain complex carbohydrate\n• Zero added oil or preservatives\n• Steady glucose release",
      searchAliases = "ruti,roti,chapati,atta ruti,bread",
      portionsJson = portions(
        portion("one_ruti", "1 Roti (35g)", 0.6f, 75, 15.0f, 3.0f, 0.5f, 2.5f, 0.3f, 60f),
        portion("two_ruti", "2 Rotis (70g)", 1.0f, 150, 30.0f, 6.0f, 1.0f, 5.0f, 0.6f, 120f),
        portion("three_ruti", "3 Rotis (105g)", 1.4f, 225, 45.0f, 9.0f, 1.5f, 7.5f, 0.9f, 180f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "chicken_curry_desi",
      name = "Desi Chicken Curry (Murgir Jhol)",
      bengaliName = "দেশি মুরগির পাতলা ঝোল",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Tender chicken cooked with onion, garlic, turmeric, and potatoes in a fragrant homestyle curry.",
      scoreExplanation = "• High protein supporting muscle repair\n• Balanced spices with anti-inflammatory turmeric\n• Lean meat preparation",
      searchAliases = "chicken,murgi,murgir jhol,chicken curry,desi chicken",
      portionsJson = portions(
        portion("small_serving", "Small Bowl (150g - 2 pcs)", 0.7f, 190, 6.0f, 20.0f, 9.0f, 1.2f, 1.5f, 280f),
        portion("regular_serving", "Medium Bowl (250g - 3 pcs + gravy)", 1.0f, 310, 10.0f, 32.0f, 15.0f, 2.0f, 2.5f, 460f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "chicken_biryani_kacchi",
      name = "Biryani / Kacchi Biryani",
      bengaliName = "কাচ্চি বিরিয়ানি / চিকেন বিরিয়ানি",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 5,
      educationalText = "A celebratory festive dish of aromatic basmati/chinigura rice, rich meat, ghee, and saffron spices. Enjoy mindfully!",
      scoreExplanation = "• High protein from meat\n• Higher score reduction due to high saturated ghee and calorie density\n• Perfectly fine as a mindful indulgence",
      searchAliases = "biryani,kacchi,polao,mutton,beef biryani",
      portionsJson = portions(
        portion("half_plate", "Half Plate (250g)", 0.7f, 390, 48.0f, 18.0f, 14.0f, 2.0f, 2.0f, 520f),
        portion("full_plate", "Full Plate (450g)", 1.0f, 720, 88.0f, 32.0f, 26.0f, 3.5f, 3.5f, 980f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "boiled_egg",
      name = "Whole Egg (Boiled / Poached)",
      bengaliName = "সিদ্ধ ডিম",
      categoryId = "meals",
      region = "Universal",
      baseScore = 10,
      educationalText = "Nature's complete nutrient capsule: high biological value protein, choline for brain health, lutein, and vitamin D.",
      scoreExplanation = "• Complete essential amino acid profile\n• Rich in brain-supportive choline\n• Minimally cooked with no added trans fats",
      searchAliases = "egg,dim,boiled egg,shiddho dim,poached",
      portionsJson = portions(
        portion("one_egg", "1 Large Egg (50g)", 0.7f, 74, 0.4f, 6.3f, 5.0f, 0.0f, 0.2f, 65f),
        portion("two_eggs", "2 Large Eggs (100g)", 1.0f, 148, 0.8f, 12.6f, 10.0f, 0.0f, 0.4f, 130f),
        portion("three_eggs", "3 Eggs (Scramble / Omelet)", 1.4f, 222, 1.2f, 18.9f, 15.0f, 0.0f, 0.6f, 195f)
      ),
      iconSymbol = "egg"
    ),
    FoodEntity(
      id = "greek_yogurt_plain",
      name = "Greek Yogurt (Plain / Tok Doi)",
      bengaliName = "টক দই (ঘরে পাতা)",
      categoryId = "meals",
      region = "Universal",
      baseScore = 10,
      educationalText = "Packed with gut-friendly live probiotics, double the protein of regular yogurt, calcium, and zero added cane sugar.",
      scoreExplanation = "• Live active probiotic cultures for gut health\n• Excellent high-density dairy protein\n• Zero refined sugars, high bioavailable calcium",
      searchAliases = "doi,tok doi,greek yogurt,yogurt,curd,probiotic",
      portionsJson = portions(
        portion("half_cup", "½ Cup (100g)", 0.6f, 65, 3.5f, 9.0f, 1.0f, 0.0f, 3.0f, 40f),
        portion("one_cup", "1 Cup (200g)", 1.0f, 130, 7.0f, 18.0f, 2.0f, 0.0f, 6.0f, 80f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "grilled_salmon_salad",
      name = "Grilled Salmon with Green Salad",
      bengaliName = "স্যামন মাছ ও সালাদ",
      categoryId = "meals",
      region = "Global",
      baseScore = 10,
      educationalText = "Premium source of EPA and DHA omega-3 fatty acids, paired with crunchy nutrient-rich salad greens.",
      scoreExplanation = "• Rich in EPA & DHA marine fatty acids\n• Clean lean protein and crisp fiber\n• Anti-inflammatory profile",
      searchAliases = "salmon,fish,salad,grilled,healthy meal",
      portionsJson = portions(
        portion("one_serving", "1 Fillet with Mixed Greens (250g)", 1.0f, 340, 6.0f, 34.0f, 18.0f, 3.5f, 2.0f, 310f)
      ),
      iconSymbol = "restaurant"
    ),

    // ---------------- SNACKS & NUTS ----------------
    FoodEntity(
      id = "raw_almonds",
      name = "Raw Almonds (Badam)",
      bengaliName = "কাঠবাদাম (কাঁচা)",
      categoryId = "snacks",
      region = "Universal",
      baseScore = 9,
      educationalText = "Crunchy powerhouse packed with Vitamin E, magnesium, prebiotic skin fiber, and heart-healthy unsaturated fats.",
      scoreExplanation = "• High in Vitamin E antioxidant and magnesium\n• Healthy unsaturated plant fats\n• Helps maintain steady fullness between meals",
      searchAliases = "almonds,badam,nuts,katha badam,dry fruits",
      portionsJson = portions(
        portion("small_handful", "Small Handful (12-14 nuts / 15g)", 0.6f, 85, 3.0f, 3.0f, 7.5f, 1.8f, 0.6f, 1f),
        portion("standard_handful", "Standard Handful (1 oz / 28g)", 1.0f, 164, 6.0f, 6.0f, 14.0f, 3.5f, 1.2f, 1f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "singara_samosa",
      name = "Singara / Samosa",
      bengaliName = "সিঙ্গারা / সমুচা",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 4,
      educationalText = "Deep-fried spiced potato pastry. A beloved street snack that is heavy in refined flour and frying oils.",
      scoreExplanation = "• Deep fried in vegetable oils (higher saturated fats)\n• Refined white flour pastry\n• Enjoy on occasion as a treat!",
      searchAliases = "singara,samosa,shingara,somucha,fried snack",
      portionsJson = portions(
        portion("one_piece", "1 Singara (75g)", 0.7f, 180, 22.0f, 3.0f, 9.5f, 1.5f, 1.0f, 260f),
        portion("two_pieces", "2 Singaras (150g)", 1.2f, 360, 44.0f, 6.0f, 19.0f, 3.0f, 2.0f, 520f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "fuchka_chotpoti",
      name = "Fuchka with Tamarind Tok (8 pcs)",
      bengaliName = "ফুচকা (টক ও চটপটি ডাল সহ)",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 6,
      educationalText = "Crispy hollow puris filled with spiced chickpea-potato and tangy tamarind broth. Moderate score for pulses and spices.",
      scoreExplanation = "• Chickpea filling provides plant fiber and protein\n• Tamarind water contains organic acids\n• Moderated score due to fried puri shells and sodium",
      searchAliases = "fuchka,phuchka,golgappa,pani puri,chotpoti",
      portionsJson = portions(
        portion("four_pcs", "4 Pieces Fuchka", 0.6f, 120, 18.0f, 3.0f, 4.0f, 2.0f, 2.5f, 240f),
        portion("eight_pcs", "1 Plate (8 Pieces)", 1.0f, 240, 36.0f, 6.0f, 8.0f, 4.0f, 5.0f, 480f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "potato_chips",
      name = "Potato Crisps / Chips",
      bengaliName = "আলুর চিপস",
      categoryId = "snacks",
      region = "Global",
      baseScore = 3,
      educationalText = "Ultra-processed fried potato snack high in saturated oils and refined salt, low in micronutrients.",
      scoreExplanation = "• Ultra-processed and deep fried\n• High sodium and refined palm oil\n• Low satiety and minimal micronutrients",
      searchAliases = "chips,crisps,potato chips,snack,lays",
      portionsJson = portions(
        portion("small_bag", "Small Bag (30g)", 0.7f, 160, 16.0f, 2.0f, 10.0f, 1.0f, 0.5f, 180f),
        portion("large_bag", "Large Sharing Bag (65g)", 1.3f, 350, 35.0f, 4.0f, 22.0f, 2.2f, 1.0f, 400f)
      ),
      iconSymbol = "lunch_dining"
    ),

    // ---------------- DRINKS ----------------
    FoodEntity(
      id = "pure_water",
      name = "Pure Water",
      bengaliName = "বিশুদ্ধ পানি",
      categoryId = "drinks",
      region = "Universal",
      baseScore = 10,
      educationalText = "Essential hydration for every cell, organ, and metabolic function in your body. Pure, zero calorie nourishment.",
      scoreExplanation = "• Essential 100% pure hydration\n• Zero additives, sugars, or preservatives\n• Positively contributes to daily wellness",
      searchAliases = "water,pani,khabar pani,hydration,drink",
      portionsJson = portions(
        portion("glass", "1 Glass (250ml)", 0.6f, 0, 0f, 0f, 0f, 0f, 0f, 0f),
        portion("bottle", "1 Bottle (500ml)", 1.0f, 0, 0f, 0f, 0f, 0f, 0f, 0f),
        portion("large_bottle", "1 Large Bottle (1 Liter)", 1.5f, 0, 0f, 0f, 0f, 0f, 0f, 0f)
      ),
      iconSymbol = "water_drop"
    ),
    FoodEntity(
      id = "rong_cha_lemon",
      name = "Black Tea with Lemon / Ginger (Rong Cha)",
      bengaliName = "রং চা (লেবু / আদা)",
      categoryId = "drinks",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Traditional black tea infused with ginger and a squeeze of fresh lemon. Packed with theaflavins and antioxidants.",
      scoreExplanation = "• Rich in tea polyphenols and antioxidant flavonoids\n• Infused with ginger and vitamin C\n• Very low calorie when consumed with light or no sugar",
      searchAliases = "cha,rong cha,black tea,lemon tea,ginger tea,tea",
      portionsJson = portions(
        portion("one_cup", "1 Cup (150ml - light sugar)", 0.7f, 20, 5.0f, 0.2f, 0.0f, 0.0f, 4.5f, 3f),
        portion("one_mug", "1 Big Mug Unsweetened (250ml)", 1.0f, 5, 1.0f, 0.3f, 0.0f, 0.0f, 0.0f, 5f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "doodh_cha_milktea",
      name = "Milk Tea with Sugar (Doodh Cha)",
      bengaliName = "দুধ চা (চিনি সহ)",
      categoryId = "drinks",
      region = "Bangladesh",
      baseScore = 6,
      educationalText = "Classic sweet spiced milk tea. Comforting and nostalgic, with moderate score based on milk fat and added sugar.",
      scoreExplanation = "• Antioxidants from brewed tea leaves\n• Moderated score due to whole milk and refined sugar\n• Enjoyable in moderation",
      searchAliases = "doodh cha,milk tea,cha,chai,sweet tea",
      portionsJson = portions(
        portion("small_cup", "1 Small Cup (120ml)", 0.7f, 75, 12.0f, 2.0f, 2.2f, 0.0f, 10.0f, 35f),
        portion("large_cup", "1 Big Cup (200ml)", 1.0f, 125, 20.0f, 3.5f, 3.8f, 0.0f, 17.0f, 60f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "cola_soft_drink",
      name = "Cola / Carbonated Soft Drink",
      bengaliName = "কোলা / কোমল পানীয়",
      categoryId = "drinks",
      region = "Global",
      baseScore = 2,
      educationalText = "Heavily sweetened carbonated beverage containing high-fructose syrup or refined sucrose with empty calories.",
      scoreExplanation = "• High refined free sugars (approx 35g per can)\n• No protein, fiber, or essential micronutrients\n• Acidic and spikes blood sugar rapidly",
      searchAliases = "coke,cola,pepsi,soft drink,soda,fizz",
      portionsJson = portions(
        portion("glass_250", "1 Glass (250ml)", 0.8f, 105, 27.0f, 0.0f, 0.0f, 0.0f, 27.0f, 25f),
        portion("can_330", "1 Can (330ml)", 1.0f, 140, 36.0f, 0.0f, 0.0f, 0.0f, 35.0f, 35f),
        portion("bottle_500", "1 Bottle (500ml)", 1.5f, 210, 54.0f, 0.0f, 0.0f, 0.0f, 53.0f, 55f)
      ),
      iconSymbol = "liquor"
    )
  )
}
