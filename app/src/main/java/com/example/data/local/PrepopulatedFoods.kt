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
    // ==========================================
    // ---------------- FRUITS ------------------
    // ==========================================
    FoodEntity(
      id = "fuji_apple",
      name = "Fuji Apple",
      bengaliName = "ফুজি আপেল",
      categoryId = "fruits",
      region = "Global",
      baseScore = 9,
      educationalText = "Crisp, sweet, and packed with soluble fiber (pectin) which gently stabilizes blood sugar and supports gut digestion.",
      scoreExplanation = "• Excellent source of dietary fiber\n• Rich in vitamin C and polyphenols\n• Minimally processed natural whole fruit\n• Low glycemic response",
      searchAliases = "apple,fuji,apel,fruit,healthy,snack,red apple",
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
      searchAliases = "avocado,hass,healthy fat,keto,superfood",
      portionsJson = portions(
        portion("half", "½ Avocado (100g)", 0.5f, 160, 8.5f, 2.0f, 14.7f, 6.7f, 0.7f, 7f),
        portion("whole", "1 Whole Avocado (200g)", 1.0f, 320, 17.0f, 4.0f, 29.4f, 13.4f, 1.4f, 14f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "banana_shobri",
      name = "Banana (Shobri / Sagar Kola)",
      bengaliName = "কলা (সবরি / সাগর)",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "A convenient natural energy booster rich in potassium, vitamin B6, and prebiotic resistant starch.",
      scoreExplanation = "• Natural sustained energy with no additives\n• High in potassium and vitamin B6\n• Gentle on the stomach and aids digestion",
      searchAliases = "banana,kola,shobri,sagor kola,champa kola,kela",
      portionsJson = portions(
        portion("half", "½ Banana", 0.5f, 52, 13.5f, 0.6f, 0.2f, 1.5f, 6.0f, 1f),
        portion("one", "1 Medium Banana (118g)", 1.0f, 105, 27.0f, 1.3f, 0.4f, 3.1f, 14.4f, 1f),
        portion("two", "2 Bananas", 1.8f, 210, 54.0f, 2.6f, 0.8f, 6.2f, 28.8f, 2f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "guava_peyara",
      name = "Fresh Guava (Peyara)",
      bengaliName = "পেয়ারা",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "One of the most nutrient-dense local fruits, boasting 4x the vitamin C of oranges and outstanding dietary fiber.",
      scoreExplanation = "• Exceptional Vitamin C content\n• Very high in dietary fiber (9g per fruit)\n• Low sugar and low glycemic index",
      searchAliases = "guava,peyara,piyara,kacha peyara,amrood",
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
      searchAliases = "mango,aam,paka aam,himsagar,langra,fazli,amrapali",
      portionsJson = portions(
        portion("slice_bowl", "1 Small Cup Sliced (120g)", 0.8f, 75, 18.0f, 1.0f, 0.5f, 2.0f, 16.0f, 2f),
        portion("one_mango", "1 Medium Mango (200g)", 1.0f, 135, 35.0f, 1.8f, 0.8f, 3.7f, 31.0f, 3f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "ripe_papaya",
      name = "Ripe Papaya (Paka Pepe)",
      bengaliName = "পাকা পেঁপে",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Soothing to the gut with natural papain digestive enzymes, vitamins A and C, and deep antioxidant carotenoids.",
      scoreExplanation = "• Contains papain digestive enzymes\n• High in Vitamin C, folate, and lycopene\n• Gentle and alkaline on the stomach",
      searchAliases = "papaya,pepe,paka pepe,papita,fruit",
      portionsJson = portions(
        portion("cup", "1 Cup Cubed (145g)", 0.8f, 62, 16.0f, 0.7f, 0.4f, 2.5f, 11.0f, 11f),
        portion("bowl", "1 Large Bowl (250g)", 1.2f, 108, 27.5f, 1.2f, 0.7f, 4.3f, 19.0f, 20f)
      ),
      iconSymbol = "spa"
    ),
    FoodEntity(
      id = "sweet_orange_malta",
      name = "Fresh Orange / Malta (Komola)",
      bengaliName = "কমলা / মাল্টা",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Hydrating citrus treasure loaded with immune-boosting bioavailable vitamin C, hesperidin, and potassium.",
      scoreExplanation = "• High Vitamin C (over 100% RDA)\n• Rich in citrus bioflavonoids\n• Natural citrus hydration and fiber",
      searchAliases = "orange,malta,komola,citrus,lebu,narangi",
      portionsJson = portions(
        portion("one_medium", "1 Medium Orange (130g)", 1.0f, 62, 15.4f, 1.2f, 0.2f, 3.1f, 12.2f, 1f),
        portion("two_small", "2 Small Maltas (200g)", 1.4f, 95, 23.5f, 1.8f, 0.3f, 4.8f, 18.5f, 2f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "dragon_fruit",
      name = "Dragon Fruit (Pitaya)",
      bengaliName = "ড্রাগন ফল",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Exotic magenta or white pulp loaded with prebiotic fiber, betalains, and tiny crunchy seeds rich in essential fatty acids.",
      scoreExplanation = "• High prebiotic fiber supports microbiome\n• Rich in betalains and polyphenol antioxidants\n• Low calorie density",
      searchAliases = "dragon fruit,dragon fol,pitaya,magenta,fruit",
      portionsJson = portions(
        portion("half", "½ Dragon Fruit (100g)", 0.6f, 60, 13.0f, 1.2f, 0.0f, 3.0f, 8.0f, 0f),
        portion("whole", "1 Whole Fruit (200g)", 1.0f, 120, 26.0f, 2.4f, 0.0f, 6.0f, 16.0f, 0f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "fresh_watermelon",
      name = "Fresh Watermelon (Tormuj)",
      bengaliName = "রসালো তরমুজ",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Super hydrating (92% pure water), rich in citrulline for cardiovascular flow and lycopene for cellular protection.",
      scoreExplanation = "• Deep cellular hydration\n• High in lycopene and L-citrulline\n• Very low calorie density per serving",
      searchAliases = "watermelon,tormuj,tarbooj,fruit,summer",
      portionsJson = portions(
        portion("slice", "1 Wedge / Slice (200g)", 0.8f, 60, 15.0f, 1.2f, 0.3f, 0.8f, 12.0f, 2f),
        portion("bowl", "2 Large Slices / Bowl (350g)", 1.2f, 105, 26.0f, 2.1f, 0.5f, 1.4f, 21.0f, 4f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "ruby_pomegranate",
      name = "Pomegranate (Bedana / Dalim)",
      bengaliName = "বেদানা / ডালিম",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Jewel-like arils concentrated with punicalagins, one of the most potent natural antioxidants known.",
      scoreExplanation = "• Outstanding punicalagin and anthocyanin antioxidant power\n• Supports arterial elasticity and heart wellness\n• Rich dietary seed fiber",
      searchAliases = "pomegranate,bedana,dalim,anar,arils,fruit",
      portionsJson = portions(
        portion("half_cup", "½ Cup Arils (87g)", 0.7f, 72, 16.3f, 1.5f, 1.0f, 3.5f, 12.0f, 3f),
        portion("one_cup", "1 Cup Arils (175g)", 1.0f, 144, 32.7f, 3.0f, 2.0f, 7.0f, 24.0f, 6f)
      ),
      iconSymbol = "spa"
    ),
    FoodEntity(
      id = "fresh_strawberries",
      name = "Fresh Strawberries / Blueberries",
      bengaliName = "স্ট্রবেরি / বেরি",
      categoryId = "fruits",
      region = "Global",
      baseScore = 10,
      educationalText = "Low sugar berries bursting with anthocyanin antioxidants, vitamin C, and manganese for anti-aging and brain health.",
      scoreExplanation = "• Low glycemic index and low net carbs\n• Powerful anthocyanin pigments\n• Boosts immune health with zero sugar crash",
      searchAliases = "strawberry,strawberries,berries,blueberry,fruit",
      portionsJson = portions(
        portion("cup", "1 Cup Whole Berries (150g)", 1.0f, 48, 11.5f, 1.0f, 0.4f, 3.0f, 7.4f, 1f),
        portion("generous", "Generous Bowl (250g)", 1.4f, 80, 19.0f, 1.7f, 0.7f, 5.0f, 12.3f, 2f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "sweet_litchi",
      name = "Sweet Litchi (Dinajpur Lichu)",
      bengaliName = "রসালো লিচু",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Delightfully fragrant summer fruit rich in vitamin C, copper, and oligonol polyphenols.",
      scoreExplanation = "• Rich in Vitamin C and oligonol\n• Refreshing natural whole fruit sweetness\n• Mindful portions recommended for sugar balance",
      searchAliases = "litchi,lichu,lychee,fruit,dinajpur",
      portionsJson = portions(
        portion("six_pcs", "6 Litchis (60g pulp)", 0.7f, 40, 10.0f, 0.5f, 0.3f, 0.8f, 9.0f, 1f),
        portion("twelve_pcs", "12 Litchis (120g pulp)", 1.0f, 80, 20.0f, 1.0f, 0.5f, 1.6f, 18.0f, 2f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "fresh_pineapple",
      name = "Queen Pineapple (Anarosh)",
      bengaliName = "আনারস",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Tangy tropical fruit containing bromelain, a proteolytic enzyme that reduces systemic inflammation and enhances protein digestion.",
      scoreExplanation = "• Contains bromelain anti-inflammatory enzyme\n• High in Manganese and Vitamin C\n• Aides digestion naturally",
      searchAliases = "pineapple,anarosh,ananas,fruit",
      portionsJson = portions(
        portion("two_slices", "2 Slices (120g)", 0.8f, 60, 15.5f, 0.6f, 0.1f, 1.7f, 12.0f, 1f),
        portion("bowl", "1 Medium Bowl (200g)", 1.0f, 100, 26.0f, 1.0f, 0.2f, 2.8f, 20.0f, 2f)
      ),
      iconSymbol = "apple"
    ),
    FoodEntity(
      id = "black_jamun",
      name = "Black Jamun / Blackberry (Kalo Jaam)",
      bengaliName = "কালো জাম",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Deep purple super-fruit revered in Ayurvedic traditions for its jamboline content, which supports natural glucose regulation.",
      scoreExplanation = "• Low glycemic index and helps balance blood sugar\n• High concentration of anthocyanins\n• Astringent digestive tonic",
      searchAliases = "jamun,kalo jaam,blackberry,black plum,fruit",
      portionsJson = portions(
        portion("handful", "1 Handful (100g / 10-12 pcs)", 1.0f, 60, 14.0f, 0.7f, 0.2f, 1.5f, 10.0f, 14f)
      ),
      iconSymbol = "spa"
    ),
    FoodEntity(
      id = "ripe_jackfruit",
      name = "Ripe Jackfruit (Paka Kathal)",
      bengaliName = "পাকা কাঁঠাল",
      categoryId = "fruits",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "The national fruit of Bangladesh, rich in dietary fiber, potassium, and carotenoids for sustained tropical energy.",
      scoreExplanation = "• Excellent whole fruit dietary fiber\n• Rich in potassium and vitamin C\n• Best enjoyed in moderate portion size",
      searchAliases = "jackfruit,kathal,paka kathal,national fruit",
      portionsJson = portions(
        portion("four_koas", "4 Pods / Koa (120g)", 0.8f, 115, 28.0f, 2.0f, 0.4f, 1.8f, 23.0f, 4f),
        portion("eight_koas", "8 Pods / Koa (240g)", 1.2f, 230, 56.0f, 4.0f, 0.8f, 3.6f, 46.0f, 8f)
      ),
      iconSymbol = "eco"
    ),

    // ==========================================
    // -------- VEGETABLES & SHAK ---------------
    // ==========================================
    FoodEntity(
      id = "palak_shak",
      name = "Spinach Shak Bhaji (Palong / Lal Shak)",
      bengaliName = "পালং শাক / লাল শাক ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Dark leafy greens cooked lightly with garlic and chili, providing iron, folate, lutein, and vital phytonutrients.",
      scoreExplanation = "• Abundant in bioavailable iron and folate\n• Strong antioxidant and carotenoid profile\n• Low in calories, very supportive of digestion",
      searchAliases = "palak,palong,shak,lal shak,spinach,bhaji,saag",
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
      searchAliases = "begun,bhaji,eggplant,brinjal,baigan,aubergine",
      portionsJson = portions(
        portion("one_slice", "1 Medium Slice (50g)", 0.6f, 65, 4.0f, 0.8f, 5.2f, 1.5f, 2.0f, 80f),
        portion("two_slices", "2 Slices (100g)", 1.0f, 130, 8.0f, 1.6f, 10.4f, 3.0f, 4.0f, 160f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "steamed_broccoli",
      name = "Steamed Broccoli",
      bengaliName = "ব্রোকলি",
      categoryId = "vegetables",
      region = "Global",
      baseScore = 10,
      educationalText = "Cruciferous powerhouse containing sulforaphane, vitamin K, and fiber for optimal cellular health.",
      scoreExplanation = "• Powerful sulforaphane antioxidant\n• Exceptional Vitamin K and C content\n• Very low calorie, supports immune defense",
      searchAliases = "broccoli,greens,steamed,veg,cruciferous",
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
      searchAliases = "sobji,labra,panchmishali,vegetables,curry,mix veg",
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
      searchAliases = "alu bhorta,aloo vorta,bhorta,vorta,potato,mash",
      portionsJson = portions(
        portion("small_ball", "Small scoop (1 tbsp / 40g)", 0.5f, 55, 8.0f, 1.0f, 2.2f, 1.0f, 0.5f, 70f),
        portion("regular", "Regular serving (2 tbsp / 80g)", 1.0f, 110, 16.0f, 2.0f, 4.5f, 2.0f, 1.0f, 140f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "lau_chingri_curry",
      name = "Bottle Gourd Curry (Lau Chingri / Lau Tarkari)",
      bengaliName = "লাউ চিংড়ি / লাউ তরকারি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Extremely cooling, low-calorie gourd simmered gently with cumin and mild spices, providing gentle hydration and gut comfort.",
      scoreExplanation = "• Very high water content (96%) and gentle fiber\n• Low in sodium and calories\n• Soothing and easy to digest",
      searchAliases = "lau,chingri,lau chingri,bottle gourd,ghia,doodhi",
      portionsJson = portions(
        portion("small_bowl", "1 Small Bowl (150g)", 0.7f, 70, 7.0f, 3.5f, 3.0f, 2.5f, 3.0f, 180f),
        portion("medium_bowl", "1 Medium Bowl (250g)", 1.0f, 115, 11.0f, 6.0f, 5.0f, 4.0f, 4.5f, 290f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "korola_bhaji",
      name = "Bitter Gourd Stir Fry (Korola / Uchhe Bhaji)",
      bengaliName = "করলা / উচ্ছে ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Known for its natural charantin and polypeptide-p compounds which support healthy insulin sensitivity.",
      scoreExplanation = "• Powerful glucose regulating phytonutrients\n• Rich in vitamin C, folate, and zinc\n• Exceptional metabolic support",
      searchAliases = "korola,uchhe,bitter gourd,karela,bhaji",
      portionsJson = portions(
        portion("small_serving", "Small Bowl (80g)", 0.7f, 60, 6.0f, 1.8f, 3.5f, 2.2f, 1.0f, 90f),
        portion("regular_serving", "Medium Bowl (140g)", 1.0f, 105, 10.0f, 3.0f, 6.0f, 3.8f, 1.5f, 160f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "dherosh_bhaji",
      name = "Stir-fried Okra / Ladyfinger (Dherosh Bhaji)",
      bengaliName = "ঢেঁড়শ ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Packed with mucilage soluble fiber that binds cholesterol and soothes gastrointestinal tracts.",
      scoreExplanation = "• High mucilage fiber lowers bad cholesterol\n• Rich in Vitamin K and C\n• Very low glycemic impact",
      searchAliases = "dherosh,okra,ladyfinger,bhindi,bhaji",
      portionsJson = portions(
        portion("small_cup", "Small Serving (100g)", 0.7f, 65, 7.0f, 2.0f, 3.5f, 3.2f, 1.5f, 110f),
        portion("medium_cup", "Medium Bowl (180g)", 1.0f, 110, 12.0f, 3.5f, 5.8f, 5.5f, 2.5f, 190f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "alu_phulkopi_curry",
      name = "Cauliflower & Potato Curry (Alu Phulkopi)",
      bengaliName = "ফুলকপি আলুর তরকারি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Fragrant winter curry with cauliflower florets, potato chunks, cumin, and ginger.",
      scoreExplanation = "• Cauliflower provides choline and glucosinolates\n• Warming digestive spices\n• Moderate score due to potato starch and oil",
      searchAliases = "phulkopi,alu phulkopi,cauliflower,gobi,curry",
      portionsJson = portions(
        portion("small_bowl", "1 Small Bowl (150g)", 0.7f, 110, 16.0f, 3.0f, 4.0f, 3.2f, 2.5f, 210f),
        portion("medium_bowl", "1 Medium Bowl (250g)", 1.0f, 180, 26.0f, 5.0f, 6.5f, 5.2f, 4.0f, 340f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "cucumber_tomato_salad",
      name = "Cucumber & Tomato Salad w/ Lemon",
      bengaliName = "শসা ও টমেটো সালাদ",
      categoryId = "vegetables",
      region = "Universal",
      baseScore = 10,
      educationalText = "Refreshing raw crunchy salad tossed with fresh green chili, coriander, and squeezed lime juice.",
      scoreExplanation = "• Raw live enzymes and deep cellular hydration\n• Zero added fat or refined sugar\n• High in lycopene, potassium, and vitamin C",
      searchAliases = "salad,shosha,tomato,cucumber,lebu,raw salad",
      portionsJson = portions(
        portion("small_plate", "Small Plate (120g)", 0.6f, 25, 5.0f, 1.0f, 0.2f, 1.8f, 3.0f, 40f),
        portion("large_plate", "Large Serving Bowl (220g)", 1.0f, 45, 9.0f, 1.8f, 0.4f, 3.2f, 5.5f, 75f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "kacha_pepe_curry",
      name = "Green Raw Papaya Curry (Kacha Pepe)",
      bengaliName = "কাঁচা পেঁপে তরকারি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Light, gentle homestyle dish praised for gut healing, digestive lightness, and enzymatic enzymes.",
      scoreExplanation = "• Outstanding digestive enzyme (papain) profile\n• Very low calorie, ultra light on digestive system\n• Gentle mineral replenishment",
      searchAliases = "pepe,kacha pepe,raw papaya,tarkari,curry",
      portionsJson = portions(
        portion("small_bowl", "1 Small Bowl (150g)", 0.7f, 65, 8.0f, 1.5f, 3.0f, 3.0f, 2.0f, 140f),
        portion("medium_bowl", "1 Medium Bowl (250g)", 1.0f, 105, 13.0f, 2.5f, 5.0f, 5.0f, 3.5f, 230f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "potol_bhaji",
      name = "Pointed Gourd Stir Fry (Potol Bhaji)",
      bengaliName = "পটল ভাজি",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Lightly scored pointed gourd sautéed with turmeric and pinch of salt, rich in dietary fiber and vitamin A.",
      scoreExplanation = "• Traditional summer vegetable with gut-cleansing fiber\n• Rich in vitamins A, C, and magnesium\n• Low carbohydrate density",
      searchAliases = "potol,pointed gourd,parwal,potol bhaji",
      portionsJson = portions(
        portion("two_pcs", "2 Pointed Gourds (80g)", 0.7f, 60, 5.0f, 1.5f, 4.0f, 2.5f, 1.5f, 90f),
        portion("four_pcs", "4 Pointed Gourds (160g)", 1.0f, 120, 10.0f, 3.0f, 8.0f, 5.0f, 3.0f, 180f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "kochur_loti",
      name = "Taro Stems with Mustard (Kochur Loti)",
      bengaliName = "কচুর লতি (সর্ষে বাটা সহ)",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Traditional Bengali delicacy packed with insoluble fiber, bioavailable calcium, iron, and iodine.",
      scoreExplanation = "• High dietary fiber supports colon health\n• Good source of calcium and plant iron\n• Flavorful with mustard and garlic",
      searchAliases = "kochu,kochur loti,taro stem,arbi,loti",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (120g)", 0.7f, 85, 9.0f, 2.5f, 4.5f, 3.5f, 1.0f, 160f),
        portion("regular_bowl", "Regular Bowl (200g)", 1.0f, 145, 15.0f, 4.0f, 7.5f, 5.8f, 1.5f, 270f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "boiled_sweet_potato",
      name = "Steamed Sweet Potato (Misti Alu)",
      bengaliName = "মিষ্টি আলু (সিদ্ধ)",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Unrefined complex carbohydrate rich in beta-carotene (Vitamin A), potassium, and gut-friendly starch.",
      scoreExplanation = "• Extremely high in Vitamin A precursor carotenoids\n• Low glycemic index compared to regular white potatoes\n• Natural whole food carbohydrate",
      searchAliases = "sweet potato,misti alu,shakarkand,yam",
      portionsJson = portions(
        portion("small", "1 Small Root (100g)", 0.7f, 86, 20.0f, 1.6f, 0.1f, 3.0f, 4.2f, 55f),
        portion("medium", "1 Medium Root (150g)", 1.0f, 130, 30.0f, 2.4f, 0.2f, 4.5f, 6.3f, 80f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "carrot_beetroot_salad",
      name = "Grated Carrot & Beetroot Salad",
      bengaliName = "গাজর ও বিটরুট সালাদ",
      categoryId = "vegetables",
      region = "Universal",
      baseScore = 10,
      educationalText = "Vibrant root vegetable salad loaded with nitrates that support nitric oxide production and blood flow.",
      scoreExplanation = "• High natural nitrates boost cardiovascular endurance\n• Abundant in lutein, zeaxanthin, and betalains\n• Raw crunch promotes dental and gut health",
      searchAliases = "carrot,beetroot,gajor,beet,salad,raw",
      portionsJson = portions(
        portion("small_cup", "1 Cup Grated (100g)", 0.7f, 42, 9.5f, 1.2f, 0.2f, 2.8f, 5.5f, 65f),
        portion("bowl", "Large Salad Bowl (180g)", 1.0f, 75, 17.0f, 2.1f, 0.4f, 5.0f, 10.0f, 115f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "sojne_data_curry",
      name = "Moringa Drumsticks & Potato Broth (Sojne Data)",
      bengaliName = "সজনে ডাঁটার পাতলা ঝোল",
      categoryId = "vegetables",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Moringa is recognized globally as a miracle tree; drumstick pods are loaded with bioavailable calcium, zinc, and isothiocyanates.",
      scoreExplanation = "• Superfood tree pod rich in bioactive antioxidants\n• Supports joint and bone health with bioavailable calcium\n• Light and cleansing broth",
      searchAliases = "sojne,sojne data,moringa,drumstick,curry",
      portionsJson = portions(
        portion("small_bowl", "1 Small Bowl (150g)", 0.7f, 70, 9.0f, 2.5f, 2.5f, 3.5f, 2.0f, 150f),
        portion("regular_bowl", "1 Medium Bowl (250g)", 1.0f, 115, 15.0f, 4.0f, 4.2f, 6.0f, 3.0f, 240f)
      ),
      iconSymbol = "eco"
    ),

    // ==========================================
    // ------- MEALS & MAIN DISHES --------------
    // ==========================================
    FoodEntity(
      id = "moshur_dal",
      name = "Red Lentil Soup (Moshur Dal)",
      bengaliName = "মসুর ডাল (পাতলা / ঘন)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Plant-based protein staple rich in folate, iron, and slow-burning complex carbohydrates.",
      scoreExplanation = "• High in plant protein and soluble fiber\n• Low glycemic index, promotes satiety\n• Tempering with cumin/garlic aids digestion",
      searchAliases = "dal,daal,moshur dal,lentils,soup,pulse",
      portionsJson = portions(
        portion("small_katori", "Small Bowl (1/2 cup)", 0.6f, 90, 14.0f, 6.0f, 1.5f, 4.0f, 1.0f, 120f),
        portion("regular_bowl", "1 Regular Bowl (1 cup / 200ml)", 1.0f, 150, 24.0f, 10.0f, 2.5f, 7.0f, 1.5f, 210f),
        portion("large_bowl", "1 Large Bowl (1.5 cups)", 1.4f, 220, 35.0f, 14.5f, 3.8f, 10.0f, 2.2f, 310f)
      ),
      iconSymbol = "soup_kitchen"
    ),
    FoodEntity(
      id = "bhuna_mug_dal",
      name = "Roasted Moong Dal with Cumin (Mug Dal)",
      bengaliName = "সোনা মুগ ডাল (ভুনা)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Gently roasted yellow lentils cooked into a rich aromatic dal, renowned for ease of digestion and clean amino acids.",
      scoreExplanation = "• Very easy on the stomach, highly bioavailable protein\n• Rich in folate, manganese, and potassium\n• Zero refined additives",
      searchAliases = "mug dal,moong dal,dal,lentils,bhuna dal",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (150ml)", 0.7f, 110, 17.0f, 7.5f, 2.0f, 4.5f, 1.0f, 140f),
        portion("regular_bowl", "Medium Bowl (220ml)", 1.0f, 165, 25.0f, 11.0f, 3.0f, 6.8f, 1.5f, 220f)
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
      searchAliases = "rice,bhat,sada bhat,plain rice,steamed rice,miniket,nazirshail",
      portionsJson = portions(
        portion("small", "Small serving (1 cup cooked / 150g)", 0.7f, 195, 43.0f, 4.0f, 0.4f, 0.6f, 0.1f, 2f),
        portion("medium", "Medium serving (1.5 cups / 225g)", 1.0f, 290, 64.0f, 6.0f, 0.6f, 0.9f, 0.1f, 3f),
        portion("large", "Large serving (2 cups / 300g)", 1.3f, 390, 86.0f, 8.0f, 0.8f, 1.2f, 0.2f, 4f)
      ),
      iconSymbol = "rice_bowl"
    ),
    FoodEntity(
      id = "brown_red_rice",
      name = "Whole Grain Brown / Red Rice (Lal Chal)",
      bengaliName = "লাল চালের ভাত",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Unpolished whole grain rice retaining its nutrient-rich bran and germ, loaded with B vitamins, magnesium, and dietary fiber.",
      scoreExplanation = "• 100% whole grain with complete bran layer\n• Lower glycemic index than polished white rice\n• Abundant in magnesium and B vitamins",
      searchAliases = "brown rice,red rice,lal chal,bhat,dheki chhata",
      portionsJson = portions(
        portion("small", "Small serving (1 cup / 150g)", 0.7f, 165, 35.0f, 3.5f, 1.2f, 2.8f, 0.5f, 4f),
        portion("medium", "Medium serving (1.5 cups / 225g)", 1.0f, 250, 52.0f, 5.3f, 1.8f, 4.2f, 0.7f, 6f)
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
      searchAliases = "khichuri,khichdi,bhuna khichuri,rice lentils,hotchpotch",
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
      educationalText = "Freshwater Rohu fish cooked in a light cumin-ginger broth with potatoes and pointed gourd. High quality lean protein.",
      scoreExplanation = "• Rich in omega fatty acids and lean protein\n• Cooked with gentle whole spices and vegetables\n• Very light on saturated fats",
      searchAliases = "mach,rui,fish,curry,macher jhol,rohu,rui mach",
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
      searchAliases = "ilish,hilsha,hilsa,shorshe ilish,mach,fish,ilish bhaja",
      portionsJson = portions(
        portion("one_piece", "1 Piece Shorshe Ilish (120g)", 1.0f, 240, 2.5f, 22.0f, 16.0f, 1.0f, 0.5f, 290f),
        portion("fried_piece", "1 Fried Ilish Piece (100g)", 0.9f, 220, 1.0f, 20.0f, 15.0f, 0.2f, 0.1f, 180f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "kachki_mola_mach_chorchori",
      name = "Small Fish Chorchori (Kachki / Mola Mach)",
      bengaliName = "কাচকি / মৌলা মাছের চচ্চড়ি",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 10,
      educationalText = "Eaten whole with bones intact, small freshwater fishes deliver immense bioavailable calcium, phosphorus, and Vitamin A.",
      scoreExplanation = "• Whole-fish consumption provides ultra-high calcium & vitamin A\n• Lean protein cooked lightly with onions and green chilies\n• Exceptional bone health support",
      searchAliases = "kachki,mola,choto mach,small fish,chorchori,fish",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (100g)", 0.7f, 120, 3.0f, 16.0f, 5.0f, 1.0f, 0.5f, 190f),
        portion("regular_bowl", "Regular Bowl (180g)", 1.0f, 210, 5.5f, 28.0f, 9.0f, 1.8f, 1.0f, 320f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "tilapia_fish_curry",
      name = "Bhetki / Tilapia Fish Fillet Curry",
      bengaliName = "ভেটকি / তেলাপিয়া মাছের ঝোল",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Tender white fish fillet simmered with turmeric, tomatoes, and cilantro. Clean high protein meal.",
      scoreExplanation = "• High protein to calorie ratio\n• Low saturated fat\n• Light, fragrant digestive gravy",
      searchAliases = "tilapia,bhetki,fish fillet,mach,curry",
      portionsJson = portions(
        portion("one_fillet", "1 Fillet with Gravy (150g)", 0.8f, 150, 4.0f, 22.0f, 5.5f, 1.2f, 1.0f, 220f),
        portion("two_fillets", "2 Fillets with Gravy (280g)", 1.0f, 275, 7.5f, 40.0f, 10.0f, 2.0f, 1.8f, 390f)
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
      searchAliases = "ruti,roti,chapati,atta ruti,bread,flatbread",
      portionsJson = portions(
        portion("one_ruti", "1 Roti (35g)", 0.6f, 75, 15.0f, 3.0f, 0.5f, 2.5f, 0.3f, 60f),
        portion("two_ruti", "2 Rotis (70g)", 1.0f, 150, 30.0f, 6.0f, 1.0f, 5.0f, 0.6f, 120f),
        portion("three_ruti", "3 Rotis (105g)", 1.4f, 225, 45.0f, 9.0f, 1.5f, 7.5f, 0.9f, 180f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "paratha_porota",
      name = "Layered Paratha / Porota (Oil / Ghee)",
      bengaliName = "পরোটা / লুচি",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 5,
      educationalText = "Flaky flatbread kneaded and fried with oil or ghee. Tasty breakfast indulgence, higher in refined calories and lipids.",
      scoreExplanation = "• Higher saturated fats from cooking oil or ghee\n• Often prepared with refined white flour\n• Best enjoyed as an occasional treat",
      searchAliases = "paratha,porota,luchi,fried bread,mughlai",
      portionsJson = portions(
        portion("one_paratha", "1 Medium Paratha (70g)", 0.8f, 220, 28.0f, 4.5f, 10.5f, 1.5f, 1.0f, 210f),
        portion("two_parathas", "2 Parathas (140g)", 1.2f, 440, 56.0f, 9.0f, 21.0f, 3.0f, 2.0f, 420f)
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
      searchAliases = "chicken,murgi,murgir jhol,chicken curry,desi chicken,curry",
      portionsJson = portions(
        portion("small_serving", "Small Bowl (150g - 2 pcs)", 0.7f, 190, 6.0f, 20.0f, 9.0f, 1.2f, 1.5f, 280f),
        portion("regular_serving", "Medium Bowl (250g - 3 pcs + gravy)", 1.0f, 310, 10.0f, 32.0f, 15.0f, 2.0f, 2.5f, 460f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "grilled_chicken_breast",
      name = "Grilled Herb Chicken Breast",
      bengaliName = "গ্রিল্ড চিকেন ব্রেস্ট",
      categoryId = "meals",
      region = "Global",
      baseScore = 10,
      educationalText = "Skinless, lean chicken breast marinated in herbs, lemon, and black pepper, grilled without heavy fats.",
      scoreExplanation = "• Ultra pure high-density protein\n• Very low saturated fat and near-zero carbs\n• Optimal for muscle recovery and metabolic rate",
      searchAliases = "grilled chicken,chicken breast,protein,lean meat,keto",
      portionsJson = portions(
        portion("one_breast", "1 Chicken Breast (150g)", 1.0f, 220, 0.5f, 44.0f, 4.5f, 0.0f, 0.0f, 180f),
        portion("large_portion", "Large Portion (220g)", 1.4f, 320, 0.8f, 64.0f, 6.5f, 0.0f, 0.0f, 260f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "beef_bhuna",
      name = "Slow Cooked Beef Bhuna (Gorur Mangsho)",
      bengaliName = "গরুর মাংস ভুনা",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 6,
      educationalText = "Rich, slow-cooked beef with caramelized onions, cardamom, and cinnamon. High in iron and B12; dense in saturated fats.",
      scoreExplanation = "• Abundant in bioavailable heme iron, zinc, and B12\n• Higher saturated fat and oil content\n• Excellent when enjoyed in balanced moderation",
      searchAliases = "beef,gorur mangsho,beef bhuna,meat,curry,red meat",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (120g - 2-3 pcs)", 0.7f, 240, 5.0f, 22.0f, 15.0f, 1.0f, 1.0f, 320f),
        portion("regular_bowl", "Regular Bowl (200g - 4-5 pcs)", 1.0f, 390, 8.0f, 36.0f, 24.0f, 1.5f, 1.5f, 520f)
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
      searchAliases = "biryani,kacchi,polao,mutton,beef biryani,dum biryani",
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
      searchAliases = "egg,dim,boiled egg,shiddho dim,poached,protein",
      portionsJson = portions(
        portion("one_egg", "1 Large Egg (50g)", 0.7f, 74, 0.4f, 6.3f, 5.0f, 0.0f, 0.2f, 65f),
        portion("two_eggs", "2 Large Eggs (100g)", 1.0f, 148, 0.8f, 12.6f, 10.0f, 0.0f, 0.4f, 130f),
        portion("three_eggs", "3 Eggs (Scramble / Omelet)", 1.4f, 222, 1.2f, 18.9f, 15.0f, 0.0f, 0.6f, 195f)
      ),
      iconSymbol = "egg"
    ),
    FoodEntity(
      id = "masala_egg_omelet",
      name = "Masala Egg Omelet (Dim Bhaji)",
      bengaliName = "ডিম ভাজি (পিঁয়াজ ও কাঁচামরিচ)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Whisked eggs folded with chopped red onions, fresh green chili, and cilantro, pan-fried lightly in mustard or olive oil.",
      scoreExplanation = "• Solid protein and essential amino acids\n• Fresh aromatics and capsaicin\n• Moderate score due to cooking oil",
      searchAliases = "dim bhaji,omelet,egg fry,masala omelet,egg",
      portionsJson = portions(
        portion("one_egg_om", "1 Egg Omelet (60g)", 0.7f, 110, 1.5f, 6.5f, 9.0f, 0.3f, 0.5f, 140f),
        portion("two_egg_om", "2 Egg Omelet (120g)", 1.0f, 210, 3.0f, 13.0f, 17.0f, 0.6f, 1.0f, 270f)
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
      searchAliases = "doi,tok doi,greek yogurt,yogurt,curd,probiotic,dahi",
      portionsJson = portions(
        portion("half_cup", "½ Cup (100g)", 0.6f, 65, 3.5f, 9.0f, 1.0f, 0.0f, 3.0f, 40f),
        portion("one_cup", "1 Cup (200g)", 1.0f, 130, 7.0f, 18.0f, 2.0f, 0.0f, 6.0f, 80f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "shahi_paneer_curry",
      name = "Fresh Paneer Curry (Tofu / Paneer)",
      bengaliName = "পনির তরকারি",
      categoryId = "meals",
      region = "Universal",
      baseScore = 8,
      educationalText = "Cottage cheese cubes gently cooked with tomatoes, bell peppers, and mild spices. Rich in dairy protein and calcium.",
      scoreExplanation = "• High vegetarian protein and calcium\n• Wholesome vegetarian meal\n• Moderate score due to dairy fats",
      searchAliases = "paneer,tofu,cottage cheese,paneer curry,sabji",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (150g)", 0.7f, 210, 8.0f, 12.0f, 15.0f, 2.0f, 3.0f, 260f),
        portion("regular_bowl", "Regular Bowl (250g)", 1.0f, 340, 13.0f, 19.0f, 24.0f, 3.2f, 4.5f, 420f)
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
      searchAliases = "salmon,fish,salad,grilled,healthy meal,omega 3",
      portionsJson = portions(
        portion("one_serving", "1 Fillet with Mixed Greens (250g)", 1.0f, 340, 6.0f, 34.0f, 18.0f, 3.5f, 2.0f, 310f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "oats_khichuri",
      name = "Rolled Oats & Vegetable Khichuri",
      bengaliName = "ওটস খিচুড়ি (সবজি সহ)",
      categoryId = "meals",
      region = "Universal",
      baseScore = 10,
      educationalText = "Heart-healthy rolled oats slow cooked with carrots, peas, moong dal, and cumin. High in beta-glucan soluble fiber.",
      scoreExplanation = "• Beta-glucan actively lowers cholesterol\n• Complex slow-release fiber sustains all-day energy\n• Low sodium, high mineral nourishment",
      searchAliases = "oats,oatmeal,oats khichuri,healthy breakfast,porridge",
      portionsJson = portions(
        portion("small_bowl", "Small Bowl (180g)", 0.7f, 160, 26.0f, 6.5f, 3.0f, 4.5f, 1.5f, 140f),
        portion("regular_bowl", "Medium Bowl (300g)", 1.0f, 260, 42.0f, 10.5f, 4.8f, 7.2f, 2.2f, 220f)
      ),
      iconSymbol = "soup_kitchen"
    ),
    FoodEntity(
      id = "panta_bhat",
      name = "Fermented Rice (Panta Bhat w/ Chili & Onion)",
      bengaliName = "পান্তা ভাত (কাঁচামরিচ ও পিঁয়াজ)",
      categoryId = "meals",
      region = "Bangladesh",
      baseScore = 8,
      educationalText = "Overnight naturally fermented rice that multiplies bioavailable iron, potassium, and beneficial probiotic lactic microbes.",
      scoreExplanation = "• Fermentation increases bioavailable micronutrients (iron & zinc)\n• Natural cooling probiotic benefits\n• Moderate score due to refined starch base",
      searchAliases = "panta bhat,fermented rice,boishakh,rice,panta",
      portionsJson = portions(
        portion("regular_plate", "1 Plate (250g with water)", 1.0f, 220, 48.0f, 4.5f, 0.5f, 1.2f, 0.2f, 110f),
        portion("with_fried_fish", "1 Plate + Fried Fish piece", 1.4f, 390, 49.0f, 22.0f, 12.0f, 1.4f, 0.3f, 290f)
      ),
      iconSymbol = "rice_bowl"
    ),

    // ==========================================
    // -------- SNACKS, NUTS & SWEETS -----------
    // ==========================================
    FoodEntity(
      id = "raw_almonds",
      name = "Raw Almonds (Katha Badam)",
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
      id = "walnuts_cashews",
      name = "Walnuts & Cashew Medley (Akhrot & Kaju)",
      bengaliName = "আখরোট ও কাজুবাদাম",
      categoryId = "snacks",
      region = "Universal",
      baseScore = 9,
      educationalText = "Brain-shaped walnuts deliver plant-based ALA Omega-3s while creamy cashews supply copper, zinc, and iron.",
      scoreExplanation = "• Superb source of plant Omega-3 ALA\n• Rich in neuro-supportive zinc and magnesium\n• Satisfying crunch without refined carbohydrates",
      searchAliases = "walnuts,cashews,kaju,akhrot,badam,nuts,dry fruits",
      portionsJson = portions(
        portion("small_handful", "Small Handful (20g)", 0.7f, 125, 4.5f, 3.5f, 11.0f, 1.2f, 1.0f, 2f),
        portion("standard_serving", "Standard Serving (35g)", 1.0f, 215, 8.0f, 6.0f, 19.0f, 2.1f, 1.8f, 3f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "roasted_chickpeas_chhola",
      name = "Roasted Spiced Chickpeas (Chhola Boot Bhaja)",
      bengaliName = "ভাজা ছোলা বুট",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Crunchy dry-roasted brown chickpeas seasoned with cumin, black salt, and chili. High in prebiotic fiber and plant protein.",
      scoreExplanation = "• High dietary fiber and plant protein\n• Dry roasted without deep frying oils\n• Slow sustained energy release",
      searchAliases = "chhola,chana,roasted chickpeas,boot bhaja,chickpea,snack",
      portionsJson = portions(
        portion("small_handful", "Small Cup (40g)", 0.7f, 140, 22.0f, 7.5f, 2.2f, 6.0f, 3.0f, 120f),
        portion("standard_cup", "Medium Bowl (80g)", 1.0f, 280, 44.0f, 15.0f, 4.4f, 12.0f, 6.0f, 230f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "jhalmuri_street",
      name = "Street Jhalmuri (Puffed Rice Snack)",
      bengaliName = "ঝালমুড়ি (সর্ষের তেল ও মসলা সহ)",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Iconic puffed rice tossed with mustard oil, chopped onions, boiled chickpeas, cucumber, and roasted spices.",
      scoreExplanation = "• Puffed rice is light and fat-free\n• Fresh raw onion, cucumber, and chickpeas add nutrients\n• Moderate score due to mustard oil and spice salt",
      searchAliases = "jhalmuri,muri,puffed rice,street food,snack",
      portionsJson = portions(
        portion("small_thonga", "Small Paper Cone / Thonga (60g)", 0.7f, 140, 26.0f, 3.5f, 3.0f, 2.0f, 1.0f, 220f),
        portion("medium_bowl", "Medium Bowl (110g)", 1.0f, 250, 46.0f, 6.0f, 5.5f, 3.5f, 1.8f, 390f)
      ),
      iconSymbol = "lunch_dining"
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
      searchAliases = "singara,samosa,shingara,somucha,fried snack,pastry",
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
      searchAliases = "fuchka,phuchka,golgappa,pani puri,chotpoti,street food",
      portionsJson = portions(
        portion("four_pcs", "4 Pieces Fuchka", 0.6f, 120, 18.0f, 3.0f, 4.0f, 2.0f, 2.5f, 240f),
        portion("eight_pcs", "1 Plate (8 Pieces)", 1.0f, 240, 36.0f, 6.0f, 8.0f, 4.0f, 5.0f, 480f)
      ),
      iconSymbol = "restaurant"
    ),
    FoodEntity(
      id = "street_chotpoti_egg",
      name = "Chotpoti with Boiled Egg (1 Plate)",
      bengaliName = "চটপটি (সিদ্ধ ডিম কুচি সহ)",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Hot yellow peas simmered in roasted spice broth, topped with grated boiled egg, cucumber, and tamarind.",
      scoreExplanation = "• Dried yellow peas deliver massive fiber and protein\n• Boiled egg adds complete amino acids\n• Tangy tamarind broth balances the palate",
      searchAliases = "chotpoti,chatpati,street food,chana,snack",
      portionsJson = portions(
        portion("half_plate", "Half Plate (180g)", 0.7f, 175, 27.0f, 8.0f, 4.0f, 6.0f, 3.0f, 340f),
        portion("full_plate", "Full Plate (320g)", 1.0f, 310, 48.0f, 14.5f, 7.0f, 10.5f, 5.0f, 620f)
      ),
      iconSymbol = "soup_kitchen"
    ),
    FoodEntity(
      id = "vegetable_spring_roll",
      name = "Crispy Vegetable Spring Roll",
      bengaliName = "ভেজিটেবল স্প্রিং রোল",
      categoryId = "snacks",
      region = "Global",
      baseScore = 5,
      educationalText = "Crisp fried pastry filled with cabbage, carrots, and onions.",
      scoreExplanation = "• Contains mixed vegetable fiber\n• Higher fat from deep frying\n• Best enjoyed occasionally",
      searchAliases = "roll,spring roll,vegetable roll,fried roll",
      portionsJson = portions(
        portion("one_roll", "1 Spring Roll (60g)", 0.7f, 130, 16.0f, 2.5f, 6.5f, 1.5f, 1.5f, 210f),
        portion("two_rolls", "2 Spring Rolls (120g)", 1.2f, 260, 32.0f, 5.0f, 13.0f, 3.0f, 3.0f, 420f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "steamed_sweet_corn",
      name = "Steamed Sweet Corn w/ Lime & Pepper (Bhutta)",
      bengaliName = "সিদ্ধ মিষ্টি ভুট্টা",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 9,
      educationalText = "Fresh sweet corn kernels steamed and seasoned with black pepper and fresh lime juice. High in lutein and fiber.",
      scoreExplanation = "• High in lutein and zeaxanthin for vision support\n• Whole grain complex carbohydrate and fiber\n• Zero oil or artificial additives",
      searchAliases = "corn,sweet corn,bhutta,steamed corn,makka",
      portionsJson = portions(
        portion("one_cup", "1 Cup Kernels / 1 Cob (140g)", 1.0f, 125, 27.0f, 4.5f, 1.8f, 3.5f, 6.0f, 40f),
        portion("large_bowl", "Large Bowl (220g)", 1.4f, 195, 42.0f, 7.0f, 2.8f, 5.5f, 9.0f, 65f)
      ),
      iconSymbol = "eco"
    ),
    FoodEntity(
      id = "peanut_butter_toast",
      name = "Peanut Butter on Whole Wheat Toast",
      bengaliName = "পিনাট বাটার হোল হুইট টোস্ট",
      categoryId = "snacks",
      region = "Global",
      baseScore = 9,
      educationalText = "Toasted whole grain bread smeared with natural 100% peanut butter. Steady energy from protein and healthy fats.",
      scoreExplanation = "• Wholesome whole wheat grain fiber\n• Heart healthy monounsaturated peanut lipids and protein\n• Keeps blood sugar completely stable",
      searchAliases = "peanut butter,toast,bread,pb toast,snack",
      portionsJson = portions(
        portion("one_slice", "1 Slice Toast with 1 tbsp PB", 0.7f, 160, 16.0f, 7.0f, 8.5f, 3.0f, 2.0f, 140f),
        portion("two_slices", "2 Slices Toast with 2 tbsp PB", 1.2f, 320, 32.0f, 14.0f, 17.0f, 6.0f, 4.0f, 280f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "mixed_seed_crunch",
      name = "Chia, Flax & Pumpkin Seed Crunch",
      bengaliName = "চিয়া, তিসি ও কুমড়ার বীজ মিক্স",
      categoryId = "snacks",
      region = "Universal",
      baseScore = 10,
      educationalText = "Concentrated super-seed mix rich in zinc, magnesium, lignans, and plant-based ALA Omega-3s.",
      scoreExplanation = "• Extremely rich in zinc, magnesium, and lignans\n• High fiber and essential fatty acids\n• Fantastic sprinkle for yogurts or salads",
      searchAliases = "seeds,chia,flaxseed,pumpkin seeds,bij,superfood",
      portionsJson = portions(
        portion("one_tbsp", "1 Tablespoon (15g)", 0.6f, 75, 4.0f, 3.5f, 5.5f, 3.0f, 0.2f, 2f),
        portion("two_tbsp", "2 Tablespoons (30g)", 1.0f, 150, 8.0f, 7.0f, 11.0f, 6.0f, 0.4f, 4f)
      ),
      iconSymbol = "spa"
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
      searchAliases = "chips,crisps,potato chips,snack,lays,kurkure",
      portionsJson = portions(
        portion("small_bag", "Small Bag (30g)", 0.7f, 160, 16.0f, 2.0f, 10.0f, 1.0f, 0.5f, 180f),
        portion("large_bag", "Large Sharing Bag (65g)", 1.3f, 350, 35.0f, 4.0f, 22.0f, 2.2f, 1.0f, 400f)
      ),
      iconSymbol = "lunch_dining"
    ),
    FoodEntity(
      id = "roshogolla_sweet",
      name = "Traditional Roshogolla / Sandesh (Misti)",
      bengaliName = "রসগোল্লা / সন্দেশ",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 4,
      educationalText = "Chhena (cottage cheese) ball soaked in concentrated sugar syrup. High in simple sucrose with moderate milk protein.",
      scoreExplanation = "• Made from cottage cheese providing some protein\n• Substantial simple refined sugar syrup\n• Best enjoyed occasionally for celebrations",
      searchAliases = "misti,sweets,roshogolla,sandesh,chamcham,dessert",
      portionsJson = portions(
        portion("one_piece", "1 Roshogolla (50g)", 0.7f, 125, 25.0f, 2.5f, 1.8f, 0.0f, 22.0f, 25f),
        portion("two_pieces", "2 Roshogollas (100g)", 1.2f, 250, 50.0f, 5.0f, 3.6f, 0.0f, 44.0f, 50f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "dark_chocolate_75",
      name = "Dark Chocolate (75%+ Cacao)",
      bengaliName = "ডার্ক চকলেট (৭৫%+)",
      categoryId = "snacks",
      region = "Global",
      baseScore = 8,
      educationalText = "Rich in cacao flavanols, theobromine, iron, and magnesium, supporting cardiovascular and cognitive wellbeing.",
      scoreExplanation = "• Rich in protective cocoa polyphenols\n• Much lower sugar than milk chocolate\n• Moderation recommended for saturated cacao butter",
      searchAliases = "chocolate,dark chocolate,cacao,sweet,dessert",
      portionsJson = portions(
        portion("two_squares", "2 Squares (20g)", 0.7f, 115, 9.0f, 1.8f, 8.5f, 2.2f, 5.0f, 5f),
        portion("four_squares", "4 Squares (40g)", 1.0f, 230, 18.0f, 3.6f, 17.0f, 4.4f, 10.0f, 10f)
      ),
      iconSymbol = "nutrition"
    ),
    FoodEntity(
      id = "tea_biscuits",
      name = "Tea Biscuits / Toast Biscuit",
      bengaliName = "চা বিস্কুট / টোস্ট বিস্কুট",
      categoryId = "snacks",
      region = "Bangladesh",
      baseScore = 4,
      educationalText = "Refined wheat flour baked with shortening oils and sugar, commonly dipped in afternoon tea.",
      scoreExplanation = "• High refined flour and palm oil fats\n• Fast digesting carbohydrates with low fiber\n• Treat as an occasional pairing with tea",
      searchAliases = "biscuit,biscuits,toast,chanachur,cookies,tea biscuit",
      portionsJson = portions(
        portion("two_biscuits", "2 Biscuits (25g)", 0.7f, 115, 18.0f, 1.8f, 4.2f, 0.5f, 6.0f, 90f),
        portion("four_biscuits", "4 Biscuits (50g)", 1.2f, 230, 36.0f, 3.6f, 8.4f, 1.0f, 12.0f, 180f)
      ),
      iconSymbol = "coffee"
    ),

    // ==========================================
    // ---------------- DRINKS ------------------
    // ==========================================
    FoodEntity(
      id = "pure_water",
      name = "Pure Water",
      bengaliName = "বিশুদ্ধ পানি",
      categoryId = "drinks",
      region = "Universal",
      baseScore = 10,
      educationalText = "Essential hydration for every cell, organ, and metabolic function in your body. Pure, zero calorie nourishment.",
      scoreExplanation = "• Essential 100% pure hydration\n• Zero additives, sugars, or preservatives\n• Positively contributes to daily wellness",
      searchAliases = "water,pani,khabar pani,hydration,drink,water bottle",
      portionsJson = portions(
        portion("glass", "1 Glass (250ml)", 0.6f, 0, 0f, 0f, 0f, 0f, 0f, 0f),
        portion("bottle", "1 Bottle (500ml)", 1.0f, 0, 0f, 0f, 0f, 0f, 0f, 0f),
        portion("large_bottle", "1 Large Bottle (1 Liter)", 1.5f, 0, 0f, 0f, 0f, 0f, 0f, 0f)
      ),
      iconSymbol = "water_drop"
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
      searchAliases = "daab,dab,coconut water,daber pani,water,hydration,tender coconut",
      portionsJson = portions(
        portion("glass", "1 Glass (250ml)", 1.0f, 45, 9.0f, 1.0f, 0.2f, 1.1f, 6.0f, 60f),
        portion("whole_daab", "1 Whole Daab (approx 400ml)", 1.5f, 75, 15.0f, 1.7f, 0.4f, 1.8f, 10.0f, 95f)
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
      searchAliases = "cha,rong cha,black tea,lemon tea,ginger tea,tea,lebu cha",
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
      searchAliases = "doodh cha,milk tea,cha,chai,sweet tea,dudh cha",
      portionsJson = portions(
        portion("small_cup", "1 Small Cup (120ml)", 0.7f, 75, 12.0f, 2.0f, 2.2f, 0.0f, 10.0f, 35f),
        portion("large_cup", "1 Big Cup (200ml)", 1.0f, 125, 20.0f, 3.5f, 3.8f, 0.0f, 17.0f, 60f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "green_tea_matcha",
      name = "Green Tea / Japanese Matcha",
      bengaliName = "গ্রিন টি / সবুজ চা",
      categoryId = "drinks",
      region = "Global",
      baseScore = 10,
      educationalText = "Concentrated in EGCG catechins and L-theanine, promoting calm alert focus, fat oxidation, and cellular repair.",
      scoreExplanation = "• High concentration of EGCG antioxidant catechins\n• L-Theanine promotes calm mental focus\n• Zero calories and zero sugar",
      searchAliases = "green tea,matcha,tea,egcg,herbal tea,greentea",
      portionsJson = portions(
        portion("cup", "1 Teacup (180ml)", 0.8f, 2, 0.4f, 0.2f, 0.0f, 0.0f, 0.0f, 2f),
        portion("mug", "1 Large Mug (300ml)", 1.0f, 4, 0.8f, 0.4f, 0.0f, 0.0f, 0.0f, 4f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "black_coffee_americano",
      name = "Black Coffee / Americano",
      bengaliName = "ব্ল্যাক কফি / আমেরিকান",
      categoryId = "drinks",
      region = "Global",
      baseScore = 9,
      educationalText = "Clean brewed coffee packed with chlorogenic acids that enhance alertness and support liver health.",
      scoreExplanation = "• Rich in chlorogenic acid polyphenols\n• Zero calories and zero sugar\n• Boosts metabolic alertness without sugar spikes",
      searchAliases = "coffee,black coffee,espresso,americano,caffeine",
      portionsJson = portions(
        portion("espresso", "1 Espresso Shot (30ml)", 0.6f, 2, 0.2f, 0.1f, 0.0f, 0.0f, 0.0f, 2f),
        portion("cup", "1 Mug Black Coffee (240ml)", 1.0f, 5, 0.6f, 0.3f, 0.0f, 0.0f, 0.0f, 5f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "turmeric_golden_milk",
      name = "Golden Turmeric Milk (Haldi Doodh)",
      bengaliName = "হলুদ দুধ (গোল্ডেন মিল্ক)",
      categoryId = "drinks",
      region = "Universal",
      baseScore = 9,
      educationalText = "Warm milk infused with organic turmeric, black pepper, and cinnamon. Curcumin delivers deep anti-inflammatory benefits.",
      scoreExplanation = "• Curcumin + piperine provide strong anti-inflammatory effects\n• Calming bedtime beverage with natural dairy calcium\n• Light honey/no sugar recommended",
      searchAliases = "turmeric milk,haldi doodh,golden milk,haldi,milk",
      portionsJson = portions(
        portion("cup", "1 Cup Warm (200ml)", 1.0f, 130, 12.0f, 7.0f, 5.0f, 0.5f, 9.0f, 95f)
      ),
      iconSymbol = "coffee"
    ),
    FoodEntity(
      id = "lemon_chia_water",
      name = "Fresh Lemon Water with Chia Seeds",
      bengaliName = "লেবু ও চিয়া সিড শরবত",
      categoryId = "drinks",
      region = "Universal",
      baseScore = 10,
      educationalText = "Fresh squeezed lime water infused with hydrated chia seeds. Alkalizing, hydrating, and packed with soluble fiber.",
      scoreExplanation = "• Soluble seed mucilage enhances hydration retention\n• Vitamin C from fresh lemon juice\n• Zero refined sugar and gentle satiety",
      searchAliases = "chia water,lemon water,lebur shorbot,chia seed,drink",
      portionsJson = portions(
        portion("glass", "1 Glass (300ml)", 1.0f, 35, 3.5f, 1.5f, 1.8f, 2.5f, 0.5f, 10f)
      ),
      iconSymbol = "water_drop"
    ),
    FoodEntity(
      id = "fresh_orange_juice",
      name = "Freshly Squeezed Orange Juice",
      bengaliName = "কমলার তাজা রস",
      categoryId = "drinks",
      region = "Universal",
      baseScore = 8,
      educationalText = "100% pure freshly squeezed orange juice. Excellent for vitamin C; best enjoyed in modest portions as liquid fruit sugars digest rapidly.",
      scoreExplanation = "• 100% natural Vitamin C and potassium\n• Zero added refined syrups\n• Less fiber than whole oranges so portion control helps",
      searchAliases = "orange juice,juice,fresh juice,komolar rosh",
      portionsJson = portions(
        portion("small_glass", "1 Small Glass (180ml)", 0.8f, 80, 19.0f, 1.2f, 0.2f, 0.4f, 16.0f, 2f),
        portion("large_glass", "1 Large Glass (300ml)", 1.2f, 135, 32.0f, 2.0f, 0.4f, 0.7f, 27.0f, 4f)
      ),
      iconSymbol = "water_drop"
    ),
    FoodEntity(
      id = "spiced_borhani",
      name = "Spiced Mint Yogurt Drink (Borhani)",
      bengaliName = "ঐতিহ্যবাহী বোরহানি",
      categoryId = "drinks",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Traditional digestive yogurt drink blended with mint, coriander, roasted cumin, black salt, and mustard.",
      scoreExplanation = "• Probiotics from yogurt and carminative spices assist digestion\n• Rich in calcium and electrolyte minerals\n• Moderate sodium from black salt and bit-laban",
      searchAliases = "borhani,burhani,yogurt drink,lassi,doi,ghol",
      portionsJson = portions(
        portion("small_glass", "1 Small Glass (150ml)", 0.7f, 90, 8.0f, 4.5f, 4.0f, 0.5f, 6.0f, 210f),
        portion("regular_glass", "1 Regular Glass (250ml)", 1.0f, 150, 13.0f, 7.5f, 6.8f, 0.8f, 10.0f, 350f)
      ),
      iconSymbol = "water_drop"
    ),
    FoodEntity(
      id = "sugarcane_juice",
      name = "Fresh Sugarcane Juice (Akher Rosh)",
      bengaliName = "আখের তাজা রস",
      categoryId = "drinks",
      region = "Bangladesh",
      baseScore = 7,
      educationalText = "Cold pressed raw sugarcane with lemon and ginger. Natural instant electrolytes and flavonoids.",
      scoreExplanation = "• Unprocessed whole plant raw juice\n• Rich in iron, magnesium, and flavonoids\n• High natural sucrose, best enjoyed post-workout or in moderation",
      searchAliases = "akher rosh,sugarcane juice,ganne ka juice,juice",
      portionsJson = portions(
        portion("glass_250", "1 Glass (250ml)", 1.0f, 130, 32.0f, 0.5f, 0.0f, 0.5f, 28.0f, 15f)
      ),
      iconSymbol = "water_drop"
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
      searchAliases = "coke,cola,pepsi,soft drink,soda,fizz,cold drink",
      portionsJson = portions(
        portion("glass_250", "1 Glass (250ml)", 0.8f, 105, 27.0f, 0.0f, 0.0f, 0.0f, 27.0f, 25f),
        portion("can_330", "1 Can (330ml)", 1.0f, 140, 36.0f, 0.0f, 0.0f, 0.0f, 35.0f, 35f),
        portion("bottle_500", "1 Bottle (500ml)", 1.5f, 210, 54.0f, 0.0f, 0.0f, 0.0f, 53.0f, 55f)
      ),
      iconSymbol = "liquor"
    ),
    FoodEntity(
      id = "energy_drink_canned",
      name = "Sweetened Energy Drink",
      bengaliName = "এনার্জি ড্রিংক",
      categoryId = "drinks",
      region = "Global",
      baseScore = 1,
      educationalText = "Carbonated blend of high refined sugar, artificial colorants, synthetic caffeine, and acidity regulators.",
      scoreExplanation = "• Extreme concentration of refined sugar (40g+ per can)\n• Artificial additives and colors\n• Sharp insulin spike followed by energy crash",
      searchAliases = "energy drink,red bull,monster,speed,tiger,drink",
      portionsJson = portions(
        portion("can_250", "1 Can (250ml)", 1.0f, 115, 28.0f, 0.0f, 0.0f, 0.0f, 27.0f, 90f),
        portion("can_500", "1 Large Can (500ml)", 1.8f, 230, 56.0f, 0.0f, 0.0f, 0.0f, 54.0f, 180f)
      ),
      iconSymbol = "liquor"
    )
  )
}
