package com.example.data.story

import com.example.domain.model.CommunicativeAct
import com.example.domain.model.DialogueChoice
import com.example.domain.model.LocationId
import com.example.domain.model.MeaningClue
import com.example.domain.model.NpcMood
import com.example.domain.model.NpcProfile
import com.example.domain.model.SceneNode

object NewHavenWorldCatalog {

    val npcs: Map<String, NpcProfile> = mapOf(
        "alex" to NpcProfile(
            id = "alex",
            name = "Alex",
            role = "Neighbor in 4B · Designer",
            homeLocation = LocationId.APARTMENT,
            personality = "Friendly, observant, remembers kindness and small details.",
            goal = "Make new neighbors feel welcome in New Haven."
        ),
        "maya" to NpcProfile(
            id = "maya",
            name = "Maya",
            role = "Head Barista · Sunbeam Café",
            homeLocation = LocationId.CAFE,
            personality = "Warm, fast-moving, loves when people describe what they like even without exact terms.",
            goal = "Keep the morning espresso bar welcoming and smooth."
        ),
        "sam" to NpcProfile(
            id = "sam",
            name = "Sam",
            role = "Regular Customer · Musician",
            homeLocation = LocationId.CAFE,
            personality = "Clumsy and well-meaning, sensitive to tone and impatience.",
            goal = "Make things right when he messes up."
        ),
        "vance" to NpcProfile(
            id = "vance",
            name = "Mr. Vance",
            role = "Owner · Sunbeam Café",
            homeLocation = LocationId.CAFE,
            personality = "Practical, fair, values composure and clear urgency over boasting.",
            goal = "Run a reliable neighborhood café."
        ),
        "elena" to NpcProfile(
            id = "elena",
            name = "Elena",
            role = "Owner · Corner Grocery",
            homeLocation = LocationId.GROCERY,
            personality = "Patient, sharp, appreciates clear communication and fairness.",
            goal = "Help neighbors stretch their weekly groceries."
        ),
        "jordan" to NpcProfile(
            id = "jordan",
            name = "Jordan",
            role = "Local Writer · Harbor Park",
            homeLocation = LocationId.PARK,
            personality = "Witty, sometimes understated or ironic; values people who read tone.",
            goal = "Enjoy honest conversations by the harbor."
        )
    )

    val clues: Map<String, MeaningClue> = mapOf(
        "clue_settle_in" to MeaningClue(
            id = "clue_settle_in",
            phrase = "settling in",
            formNote = "Phrasal verb: settle + in",
            contextualMeaning = "Getting comfortable in a new home or neighborhood.",
            conceptMetaphor = "LIFE CHANGE IS A JOURNEY -> arriving and finding steady footing.",
            visualHint = "Alex glances at your half-unpacked moving boxes by the doorway.",
            deepReveal = "When someone asks 'How are you settling in?', they aren't asking if you're sitting down — they're asking how your move and new life are feeling.",
            exampleContext = "Maple St. Apartment · Day 1"
        ),
        "clue_make_it_up" to MeaningClue(
            id = "clue_make_it_up",
            phrase = "make it up to you",
            formNote = "Idiomatic expression: make + it + up + to someone",
            contextualMeaning = "Do something good to compensate for a mistake or inconvenience.",
            conceptMetaphor = "FAIRNESS IS BALANCE -> restoring balance after tipping it.",
            visualHint = "Sam reaches for napkins and pulls out his wallet looking genuinely sorry.",
            deepReveal = "This never means 'inventing a story' here. In a social mishap, 'Let me make it up to you' means 'Let me fix this by doing something nice for you (like buying your next coffee).'",
            exampleContext = "Sunbeam Café · Day 1"
        ),
        "clue_money_back" to MeaningClue(
            id = "clue_money_back",
            phrase = "Can I get my money back?",
            formNote = "Paraphrase for 'refund'",
            contextualMeaning = "Requesting a return of payment when an item isn't what you wanted.",
            conceptMetaphor = "COMMUNICATION IS TOOL USE -> reaching the goal even without the single vocabulary label.",
            visualHint = "You hold the unopened carton and your paper receipt across the counter.",
            deepReveal = "You don't need to know the word 'refund' to get one! Explaining 'I paid for this, but I got the wrong one — can I get my money back?' achieves the exact real-world action.",
            exampleContext = "Corner Grocery · Day 2"
        ),
        "clue_nailed_it" to MeaningClue(
            id = "clue_nailed_it",
            phrase = "You really nailed it!",
            formNote = "Idiom: nail + it",
            contextualMeaning = "You performed something brilliantly or got it exactly right.",
            conceptMetaphor = "SUCCESS IS HITTING THE TARGET -> striking a nail cleanly on the head.",
            visualHint = "Maya beams and gives you a quick thumbs-up after the customer walks away happy.",
            deepReveal = "Nothing to do with hammers or fingernails — in everyday English, 'You nailed it!' is warm, enthusiastic praise.",
            exampleContext = "Sunbeam Café · Day 2"
        ),
        "clue_thats_interesting" to MeaningClue(
            id = "clue_thats_interesting",
            phrase = "That's interesting.",
            formNote = "Tone-dependent phrase",
            contextualMeaning = "Can signal genuine curiosity, polite disagreement, or dry irony depending on body language.",
            conceptMetaphor = "POLITENESS IS DISTANCE -> softening disagreement with neutral words.",
            visualHint = "Notice Jordan's raised eyebrow and pause before speaking — tone carries the real message.",
            deepReveal = "Never rely on words alone. With a warm lean-in it means 'tell me more'; with a pause and raised brow, it gently signals skepticism.",
            exampleContext = "Harbor Park · Day 3"
        ),
        "clue_critical_urgent" to MeaningClue(
            id = "clue_critical_urgent",
            phrase = "critical (decision / shortage)",
            formNote = "Polysemy · Layer 1: Form 'critical'",
            contextualMeaning = "Urgent, decisive, or essential to survival/success in the moment.",
            conceptMetaphor = "A CRISIS IS A TURNING POINT -> one choice changes the outcome.",
            visualHint = "Maya checks the empty bean hopper while a line of commuters forms at the door.",
            deepReveal = "Same word form ('critical'), very different context from 'being critical of someone.' Here, 'critical' means vital and urgent.",
            exampleContext = "Sunbeam Café · Day 4"
        ),
        "clue_critical_judgment" to MeaningClue(
            id = "clue_critical_judgment",
            phrase = "critical of yourself",
            formNote = "Polysemy · Contrastive Context",
            contextualMeaning = "Judging faults harshly or pointing out mistakes.",
            conceptMetaphor = "JUDGING IS WEIGHING FAULTS -> inspecting closely for flaws.",
            visualHint = "Jordan smiles reassuringly while pointing out you actually handled the morning well.",
            deepReveal = "Notice how 'critical' shifts meaning completely depending on whether it describes a situation ('critical shortage') or an attitude toward a person ('critical of yourself').",
            exampleContext = "Harbor Park · Day 4"
        ),
        "clue_cant_afford" to MeaningClue(
            id = "clue_cant_afford",
            phrase = "We can't afford to ignore this.",
            formNote = "Conceptual Metaphor · Value & Cost",
            contextualMeaning = "The risk or cost of doing nothing is far too high.",
            conceptMetaphor = "IMPORTANCE IS COST / WEIGHT -> ignoring a problem carries a heavy price.",
            visualHint = "Mr. Vance looks up from his ledger the moment you frame the risk in business terms.",
            deepReveal = "Using 'can't afford to' isn't just about cash — it makes an issue feel urgent and weighty to a decision-maker.",
            exampleContext = "Sunbeam Café · Day 5"
        ),
        "clue_push_luck" to MeaningClue(
            id = "clue_push_luck",
            phrase = "Don't push your luck.",
            formNote = "Idiom: push + one's luck",
            contextualMeaning = "Stop asking for more favors or taking extra risks after someone already helped you.",
            conceptMetaphor = "GOODWILL IS A FINITE RESOURCE -> pressing too hard breaks it.",
            visualHint = "Elena shakes her head lightly, warning Sam not to ask for even more freebies.",
            deepReveal = "Not about physically pushing anything! It's a social boundary cue meaning 'You've already gotten a good deal — stop before you lose goodwill.'",
            exampleContext = "Corner Grocery · Day 6"
        )
    )

    val scenes: List<SceneNode> = listOf(
        // DAY 1: Scene 1 — Neighbor Alex
        SceneNode(
            id = "day1_neighbor_alex",
            day = 1,
            chapterTitle = "Chapter 01 · Your First Day",
            locationId = LocationId.APARTMENT,
            npcId = "alex",
            npcName = "Alex",
            npcRole = "Neighbor in 4B",
            initialMood = NpcMood.WARM,
            bodyLanguageCue = "Holding a coffee mug, leaning casually by the open doorway with a welcoming smile.",
            environmentAction = "Morning sunlight fills the hallway beside your stacked moving boxes. You have $50 in your pocket and no job yet.",
            npcLine = "Hey! You must be the new neighbor. How are you settling in?",
            highlightedClue = clues["clue_settle_in"],
            subtextNote = "Alex is genuinely curious and offering an easy opening into neighborhood life.",
            choices = listOf(
                DialogueChoice(
                    id = "d1_alex_warm",
                    text = "Pretty well, thanks! I'm still getting used to the neighborhood.",
                    communicativeAct = CommunicativeAct.GREETING,
                    trustDelta = 2,
                    nextSceneId = "day1_cafe_order",
                    memorySummary = "You greeted Alex warmly on moving day and admitted you're still getting used to New Haven.",
                    worldReactionNote = "Alex smiled: 'Welcome to Maple Street! Stop by Sunbeam Café down the block if you need good espresso.'",
                    boostListening = 4, boostSpeaking = 5, boostFluency = 4
                ),
                DialogueChoice(
                    id = "d1_alex_practical",
                    text = "I'm fine. Where is the nearest grocery store?",
                    communicativeAct = CommunicativeAct.PRACTICAL_ASK,
                    trustDelta = 1,
                    nextSceneId = "day1_cafe_order",
                    memorySummary = "You kept things brief with Alex and asked straight away where to buy groceries.",
                    worldReactionNote = "Alex pointed down Harbor Ave: 'Corner Grocery is two blocks away. Sunbeam Café is right before it!'",
                    boostListening = 3, boostSpeaking = 4, boostVocabulary = 3
                ),
                DialogueChoice(
                    id = "d1_alex_honest",
                    text = "Could be better. I don't know anyone here yet.",
                    communicativeAct = CommunicativeAct.VULNERABLE_SHARE,
                    trustDelta = 3,
                    nextSceneId = "day1_cafe_order",
                    memorySummary = "You were honest with Alex about not knowing anyone in the city yet.",
                    worldReactionNote = "Alex's face softened: 'Hey, now you know me! Come grab coffee at Sunbeam Café — I'll introduce you around later.'",
                    boostListening = 4, boostSpeaking = 5, boostPragmatics = 5, boostFluency = 4
                )
            )
        ),

        // DAY 1: Scene 2 — Sunbeam Café Order (Language as Action + Paraphrase)
        SceneNode(
            id = "day1_cafe_order",
            day = 1,
            chapterTitle = "Chapter 01 · Your First Day",
            locationId = LocationId.CAFE,
            npcId = "maya",
            npcName = "Maya",
            npcRole = "Head Barista",
            initialMood = NpcMood.WARM,
            bodyLanguageCue = "Wiping the espresso wand, looking up brightly across the wooden counter.",
            environmentAction = "You step into Sunbeam Café. The chalkboard menu lists unfamiliar drink names. You want coffee but aren't sure what to call it.",
            npcLine = "Morning! Welcome to Sunbeam. What can I get for you?",
            subtextNote = "Your phrasing shapes the interaction: polite request, recommendation, or describing what you want without the exact word.",
            choices = listOf(
                DialogueChoice(
                    id = "d1_maya_paraphrase",
                    text = "I'd like something strong, but not too bitter.",
                    communicativeAct = CommunicativeAct.PARAPHRASE,
                    trustDelta = 2,
                    moneyDelta = -4,
                    nextSceneId = "day1_cafe_spill",
                    memorySummary = "At Sunbeam Café, you described your taste without knowing the menu name.",
                    worldReactionNote = "Maya grinned: 'Gotcha! Let me make you an oat cortado — rich espresso, smooth milk, zero bitterness. That's $4.'",
                    boostSpeaking = 6, boostAdaptability = 8, boostVocabulary = 4
                ),
                DialogueChoice(
                    id = "d1_maya_recommend",
                    text = "What's good here? I'll have whatever you recommend.",
                    communicativeAct = CommunicativeAct.ASK_RECOMMENDATION,
                    trustDelta = 2,
                    moneyDelta = -4,
                    nextSceneId = "day1_cafe_spill",
                    memorySummary = "You trusted Maya's recommendation for your first coffee in New Haven.",
                    worldReactionNote = "Maya beamed: 'Love an open mind! Our honey-cinnamon latte is a local favorite. $4!'",
                    boostListening = 5, boostPragmatics = 4, boostFluency = 4
                ),
                DialogueChoice(
                    id = "d1_maya_polite",
                    text = "Could I get a cappuccino, please?",
                    communicativeAct = CommunicativeAct.POLITE_REQUEST,
                    trustDelta = 1,
                    moneyDelta = -4,
                    nextSceneId = "day1_cafe_spill",
                    memorySummary = "You ordered a cappuccino politely at Sunbeam Café.",
                    worldReactionNote = "Maya nodded: 'Coming right up! That'll be $4.'",
                    boostSpeaking = 4, boostPragmatics = 3
                )
            )
        ),

        // DAY 1: Scene 3 — The Unexpected Spill (Meaning Discovery)
        SceneNode(
            id = "day1_cafe_spill",
            day = 1,
            chapterTitle = "Chapter 01 · Your First Day",
            locationId = LocationId.CAFE,
            npcId = "sam",
            npcName = "Sam",
            npcRole = "Café Customer",
            initialMood = NpcMood.APOLOGETIC,
            bodyLanguageCue = "Wide-eyed, grabbing a stack of napkins with both hands, shoulders hunched.",
            environmentAction = "Just as you turn from the counter, another customer bumps your elbow — dark espresso splashes onto your sleeve!",
            npcLine = "Oh, I'm so sorry! That was completely my fault. Let me make it up to you.",
            highlightedClue = clues["clue_make_it_up"],
            subtextNote = "Observe Sam's panicked apology and napkins — what is he actually offering?",
            choices = listOf(
                DialogueChoice(
                    id = "d1_sam_kind",
                    text = "No problem. Accidents happen — don't worry about it.",
                    communicativeAct = CommunicativeAct.EMPATHIZE,
                    trustDelta = 3,
                    moneyDelta = 4,
                    nextSceneId = "day1_cafe_hiring",
                    unlocksClueId = "clue_make_it_up",
                    memorySummary = "When Sam spilled coffee on you, you stayed calm and forgave him.",
                    worldReactionNote = "Sam exhaled with relief and insisted on paying for a fresh cup (+$4 back in your pocket).",
                    boostComprehension = 6, boostPragmatics = 6, boostFluency = 4
                ),
                DialogueChoice(
                    id = "d1_sam_accept",
                    text = "It's okay, thanks. Maybe just a fresh coffee would be great.",
                    communicativeAct = CommunicativeAct.POLITE_REQUEST,
                    trustDelta = 2,
                    moneyDelta = 4,
                    nextSceneId = "day1_cafe_hiring",
                    unlocksClueId = "clue_make_it_up",
                    memorySummary = "You accepted Sam's offer politely after the coffee spill.",
                    worldReactionNote = "Sam smiled gratefully and covered a fresh cup for you (+$4).",
                    boostComprehension = 5, boostSpeaking = 4, boostPragmatics = 4
                ),
                DialogueChoice(
                    id = "d1_sam_angry",
                    text = "You should be more careful! Look at my shirt!",
                    communicativeAct = CommunicativeAct.ASSERT_BOUNDARY,
                    trustDelta = -2,
                    nextSceneId = "day1_cafe_hiring",
                    unlocksClueId = "clue_make_it_up",
                    memorySummary = "You snapped at Sam when he spilled coffee on your shirt.",
                    worldReactionNote = "Sam stiffened, apologized quietly, and hurried toward the door. Nearby, the café manager noticed the tension.",
                    boostSpeaking = 3, boostPragmatics = 1
                )
            )
        ),

        // DAY 1: Scene 4 — The Job Opportunity (Consequence System)
        SceneNode(
            id = "day1_cafe_hiring",
            day = 1,
            chapterTitle = "Chapter 01 · Your First Day",
            locationId = LocationId.CAFE,
            npcId = "vance",
            npcName = "Mr. Vance",
            npcRole = "Café Owner",
            initialMood = NpcMood.BUSY,
            bodyLanguageCue = "Clipboard in hand, glancing between the espresso line and a taped window sign.",
            environmentAction = "Beside the door hangs a hand-lettered sign: 'WE'RE HIRING! Part-time barista wanted.' You walk up to ask.",
            npcLine = "We do need an extra pair of hands for the morning rush. Have you got any experience?",
            subtextNote = "There isn't one 'correct' textbook answer — each choice creates a different impression on Mr. Vance.",
            choices = listOf(
                DialogueChoice(
                    id = "d1_vance_honest_money",
                    text = "Not really, I just moved here and I need money.",
                    communicativeAct = CommunicativeAct.VULNERABLE_SHARE,
                    trustDelta = 0,
                    jobStatusUpdate = "Waitlisted (Callback later)",
                    completesCurrentBeat = true,
                    memorySummary = "You told Mr. Vance bluntly that you needed money.",
                    worldReactionNote = "Mr. Vance raised a brow: 'Fair enough, you're honest. Leave your name — if someone drops out, I'll call you.'",
                    boostSpeaking = 3, boostPragmatics = 2
                ),
                DialogueChoice(
                    id = "d1_vance_pitch",
                    text = "I'm a quick learner and hardworking — I'd love to help your team.",
                    communicativeAct = CommunicativeAct.EXPLAIN_EXPERIENCE,
                    trustDelta = 3,
                    jobStatusUpdate = "Trial Shift Tomorrow!",
                    completesCurrentBeat = true,
                    memorySummary = "You pitched your work ethic clearly and earned a barista trial shift at Sunbeam Café.",
                    worldReactionNote = "Mr. Vance nodded approvingly: 'Good attitude matters more than fancy resumes. Come in tomorrow morning for a paid trial shift.'",
                    boostSpeaking = 6, boostPragmatics = 6, boostAdaptability = 5
                ),
                DialogueChoice(
                    id = "d1_vance_boast",
                    text = "I think I'm better than most people applying for this.",
                    communicativeAct = CommunicativeAct.ASSERT_BOUNDARY,
                    trustDelta = -1,
                    jobStatusUpdate = "Uncertain (Needs proof)",
                    completesCurrentBeat = true,
                    memorySummary = "You came across very bold in your Sunbeam Café pitch.",
                    worldReactionNote = "Mr. Vance squinted slightly: 'Confidence is fine, kid, but teamwork matters here. Come back tomorrow and show me, don't just tell me.'",
                    boostSpeaking = 4, boostPragmatics = 2
                )
            )
        ),

        // DAY 2: Scene 1 — Corner Grocery Paraphrase (Adaptability)
        SceneNode(
            id = "day2_grocery_paraphrase",
            day = 2,
            chapterTitle = "Chapter 02 · Making Ends Meet",
            locationId = LocationId.GROCERY,
            npcId = "elena",
            npcName = "Elena",
            npcRole = "Shopkeeper · Corner Grocery",
            initialMood = NpcMood.CURIOUS,
            bodyLanguageCue = "Scanning fruit crates behind the register, glancing at the unopened milk carton in your hand.",
            environmentAction = "You bought groceries earlier, then realized you grabbed sweetened soy milk by mistake. You don't know the English word 'refund'.",
            npcLine = "Hey there! Forgot something for breakfast, or is there an issue with your bag?",
            highlightedClue = clues["clue_money_back"],
            subtextNote = "You don't need the exact noun 'refund' — communicate your real need with words you already have.",
            choices = listOf(
                DialogueChoice(
                    id = "d2_elena_paraphrase",
                    text = "I paid for this, but I don't want it anymore. Can I get my money back?",
                    communicativeAct = CommunicativeAct.PARAPHRASE,
                    trustDelta = 2,
                    moneyDelta = 6,
                    nextSceneId = "day2_cafe_shift",
                    unlocksClueId = "clue_money_back",
                    memorySummary = "At Corner Grocery, you solved a wrong purchase by paraphrasing without knowing 'refund'.",
                    worldReactionNote = "Elena smiled warmly: 'Of course — an easy refund! Here's your $6 back, and oat milk is right on shelf two.'",
                    boostAdaptability = 10, boostSpeaking = 6, boostFluency = 5
                ),
                DialogueChoice(
                    id = "d2_elena_swap",
                    text = "I picked the wrong one by mistake. Could I swap it for oat milk?",
                    communicativeAct = CommunicativeAct.POLITE_REQUEST,
                    trustDelta = 2,
                    nextSceneId = "day2_cafe_shift",
                    unlocksClueId = "clue_money_back",
                    memorySummary = "You politely asked Elena to exchange the wrong carton.",
                    worldReactionNote = "Elena nodded: 'No problem at all! Go grab the blue carton from the chiller.'",
                    boostSpeaking = 5, boostPragmatics = 5, boostAdaptability = 6
                ),
                DialogueChoice(
                    id = "d2_elena_keep",
                    text = "Never mind, it's okay. I'll just keep it.",
                    communicativeAct = CommunicativeAct.EMPATHIZE,
                    trustDelta = 0,
                    moneyDelta = -6,
                    nextSceneId = "day2_cafe_shift",
                    memorySummary = "You hesitated to speak up at Corner Grocery and kept the wrong item.",
                    worldReactionNote = "You walked out $6 lighter — a quiet reminder that speaking up changes real outcomes.",
                    boostListening = 2
                )
            )
        ),

        // DAY 2: Scene 2 — Sunbeam Café Shift (Meaning Discovery: "You nailed it!")
        SceneNode(
            id = "day2_cafe_shift",
            day = 2,
            chapterTitle = "Chapter 02 · Making Ends Meet",
            locationId = LocationId.CAFE,
            npcId = "maya",
            npcName = "Maya",
            npcRole = "Head Barista",
            initialMood = NpcMood.IMPRESSED,
            bodyLanguageCue = "Beaming from behind the counter, gesturing proudly toward the happy commuter walking out.",
            environmentAction = "You stepped in during a busy rush and helped a rushed customer customize an iced latte in English.",
            npcLine = "Whoa, that guy usually complains about everything — and he left smiling! You really nailed it!",
            highlightedClue = clues["clue_nailed_it"],
            subtextNote = "Notice Maya's expression and what just happened with the customer before guessing what 'nailed it' means.",
            choices = listOf(
                DialogueChoice(
                    id = "d2_maya_grateful",
                    text = "Thanks, Maya! I just tried to listen carefully to what he wanted.",
                    communicativeAct = CommunicativeAct.GREETING,
                    trustDelta = 2,
                    moneyDelta = 28,
                    jobStatusUpdate = "Part-Time Barista ($28/shift)",
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_nailed_it",
                    memorySummary = "You handled a tough customer at Sunbeam Café, earned $28, and got hired part-time.",
                    worldReactionNote = "Mr. Vance handed you $28 and an apron: 'Welcome to Sunbeam Café officially!'",
                    boostListening = 6, boostComprehension = 6, boostFluency = 5
                ),
                DialogueChoice(
                    id = "d2_maya_ask",
                    text = "Wait — 'nailed it'? Does that mean I did a good job?",
                    communicativeAct = CommunicativeAct.PRACTICAL_ASK,
                    trustDelta = 2,
                    moneyDelta = 28,
                    jobStatusUpdate = "Part-Time Barista ($28/shift)",
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_nailed_it",
                    memorySummary = "You checked the natural meaning of 'nailed it' with Maya and earned your first $28 shift pay.",
                    worldReactionNote = "Maya laughed: 'Yes! Means you got it 100% right. Here's your $28 for today's shift!'",
                    boostVocabulary = 7, boostComprehension = 7, boostSpeaking = 4
                )
            )
        ),

        // DAY 3: Scene 1 — Reading Between the Lines at Harbor Park
        SceneNode(
            id = "day3_park_subtext",
            day = 3,
            chapterTitle = "Chapter 03 · Reading People",
            locationId = LocationId.PARK,
            npcId = "jordan",
            npcName = "Jordan",
            npcRole = "Local Writer",
            initialMood = NpcMood.SKEPTICAL,
            bodyLanguageCue = "Pauses for two seconds, tilts head slightly, eyebrows raised with a polite but flat half-smile.",
            environmentAction = "You meet Jordan on a sunny bench by the harbor. You suggest that New Haven looks calm and effortless all year round.",
            npcLine = "Hmm... New Haven, completely effortless? That's interesting.",
            highlightedClue = clues["clue_thats_interesting"],
            subtextNote = "Watch the pause and raised eyebrow! Is Jordan agreeing, or gently disagreeing without arguing?",
            choices = listOf(
                DialogueChoice(
                    id = "d3_jordan_read_subtext",
                    text = "Wait — you don't really think it's effortless, do you? What's it actually like?",
                    communicativeAct = CommunicativeAct.READ_SUBTEXT,
                    trustDelta = 3,
                    nextSceneId = "day3_alex_followup",
                    unlocksClueId = "clue_thats_interesting",
                    memorySummary = "You read Jordan's subtle skepticism behind 'That's interesting' and invited an honest answer.",
                    worldReactionNote = "Jordan chuckled warmly: 'Good ear! Winter storms here test everyone, though the neighbors make up for it.'",
                    boostPragmatics = 10, boostComprehension = 8, boostListening = 5
                ),
                DialogueChoice(
                    id = "d3_jordan_literal",
                    text = "Yeah! Everything just works smoothly here.",
                    communicativeAct = CommunicativeAct.VULNERABLE_SHARE,
                    trustDelta = 0,
                    nextSceneId = "day3_alex_followup",
                    unlocksClueId = "clue_thats_interesting",
                    memorySummary = "You took Jordan's 'That's interesting' at face value and missed the polite skepticism.",
                    worldReactionNote = "Jordan gave a polite nod and changed the subject to the harbor boats.",
                    boostSpeaking = 3, boostPragmatics = 2
                )
            )
        ),

        // DAY 3: Scene 2 — World Memory Callback with Alex
        SceneNode(
            id = "day3_alex_followup",
            day = 3,
            chapterTitle = "Chapter 03 · Reading People",
            locationId = LocationId.APARTMENT,
            npcId = "alex",
            npcName = "Alex",
            npcRole = "Neighbor in 4B",
            initialMood = NpcMood.WARM,
            bodyLanguageCue = "Holding a spare bike helmet and a bright yellow flyer for Sunday's harbor potluck.",
            environmentAction = "Three days in, Maple Street already feels a little more like home. Alex catches you on the stairs.",
            npcLine = "Hey! Heard you've been busy down at Sunbeam and Harbor Park. Want to join a small neighborhood dinner on Sunday?",
            subtextNote = "World Memory shapes how neighbors treat you over time.",
            choices = listOf(
                DialogueChoice(
                    id = "d3_alex_join",
                    text = "I'd love that! Should I bring anything?",
                    communicativeAct = CommunicativeAct.GREETING,
                    trustDelta = 3,
                    completesCurrentBeat = true,
                    memorySummary = "You accepted Alex's invitation to the Maple Street neighborhood dinner.",
                    worldReactionNote = "Alex smiled: 'Just bring yourself — and maybe a story from your first week!'",
                    boostFluency = 6, boostPragmatics = 5, boostSpeaking = 5
                ),
                DialogueChoice(
                    id = "d3_alex_nervous",
                    text = "I'm a bit nervous my English isn't good enough for a group dinner.",
                    communicativeAct = CommunicativeAct.VULNERABLE_SHARE,
                    trustDelta = 3,
                    completesCurrentBeat = true,
                    memorySummary = "You shared your nervousness about group English with Alex, who reassured you.",
                    worldReactionNote = "Alex laughed gently: 'Nobody cares about perfect grammar over pasta. You're already doing great.'",
                    boostSpeaking = 6, boostPragmatics = 6, boostAdaptability = 5
                )
            )
        ),

        // DAY 4: Scene 1 — Same Word, Different Contexts ("critical" at Café)
        SceneNode(
            id = "day4_cafe_critical",
            day = 4,
            chapterTitle = "Chapter 04 · Layers of Meaning",
            locationId = LocationId.CAFE,
            npcId = "maya",
            npcName = "Maya",
            npcRole = "Head Barista",
            initialMood = NpcMood.BUSY,
            bodyLanguageCue = "Pointing urgently at an empty espresso bean jar while customers line up.",
            environmentAction = "7:45 AM rush! The backup delivery is late and you have only enough beans for ten more drinks.",
            npcLine = "We're in a critical situation — we have to decide right now whether to run to Corner Grocery or switch to pour-over only. This is a critical decision!",
            highlightedClue = clues["clue_critical_urgent"],
            subtextNote = "Hear the alarm in the café: 'critical' here isn't criticism — it's an urgent turning point.",
            choices = listOf(
                DialogueChoice(
                    id = "d4_maya_act",
                    text = "I'll run to Elena's Corner Grocery right now while you brew pour-over!",
                    communicativeAct = CommunicativeAct.FRAME_URGENCY,
                    trustDelta = 3,
                    moneyDelta = 24,
                    nextSceneId = "day4_park_critical_contrast",
                    unlocksClueId = "clue_critical_urgent",
                    memorySummary = "You understood the urgency of the 'critical decision' and saved Sunbeam's morning rush (+$24).",
                    worldReactionNote = "You sprinted two blocks and got back in time — Maya called you a lifesaver!",
                    boostComprehension = 8, boostVocabulary = 6, boostAdaptability = 6
                ),
                DialogueChoice(
                    id = "d4_maya_calm",
                    text = "Let's tell customers honestly that espresso is limited for 15 minutes.",
                    communicativeAct = CommunicativeAct.POLITE_REQUEST,
                    trustDelta = 2,
                    moneyDelta = 20,
                    nextSceneId = "day4_park_critical_contrast",
                    unlocksClueId = "clue_critical_urgent",
                    memorySummary = "You handled the critical bean shortage calmly by communicating clearly with guests.",
                    worldReactionNote = "Most guests switched to drip coffee smoothly — crises don't rattle you.",
                    boostComprehension = 7, boostSpeaking = 5, boostPragmatics = 5
                )
            )
        ),

        // DAY 4: Scene 2 — Contrasting "critical" at Harbor Park
        SceneNode(
            id = "day4_park_critical_contrast",
            day = 4,
            chapterTitle = "Chapter 04 · Layers of Meaning",
            locationId = LocationId.PARK,
            npcId = "jordan",
            npcName = "Jordan",
            npcRole = "Local Writer",
            initialMood = NpcMood.WARM,
            bodyLanguageCue = "Sitting beside you with two teas, nodding calmly toward the harbor.",
            environmentAction = "After the chaotic morning shift, you mention feeling clumsy with a few English phrases during the rush.",
            npcLine = "Hey, don't be too critical of yourself. Everyone heard you clearly when it mattered.",
            highlightedClue = clues["clue_critical_judgment"],
            subtextNote = "Notice how 'critical' shifted from 'urgent crisis' this morning to 'harshly judging' right now!",
            choices = listOf(
                DialogueChoice(
                    id = "d4_jordan_notice",
                    text = "Funny how 'critical' meant an emergency this morning, and judging myself just now!",
                    communicativeAct = CommunicativeAct.READ_SUBTEXT,
                    trustDelta = 3,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_critical_judgment",
                    memorySummary = "You connected two distinct contextual meanings of 'critical' in one day.",
                    worldReactionNote = "Jordan smiled: 'Right? Same word on paper, totally different worlds in real life.'",
                    boostVocabulary = 10, boostComprehension = 9, boostPragmatics = 6
                ),
                DialogueChoice(
                    id = "d4_jordan_thanks",
                    text = "Thanks, Jordan. It helps to hear that after a crazy morning.",
                    communicativeAct = CommunicativeAct.GREETING,
                    trustDelta = 2,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_critical_judgment",
                    memorySummary = "You accepted Jordan's encouragement after surviving the café rush.",
                    worldReactionNote = "Jordan raised his tea cup: 'To surviving week one!'",
                    boostListening = 5, boostFluency = 5
                )
            )
        ),

        // DAY 5: Persuading Mr. Vance (Conceptual Metaphors of Importance)
        SceneNode(
            id = "day5_vance_persuade",
            day = 5,
            chapterTitle = "Chapter 05 · Weight of Words",
            locationId = LocationId.CAFE,
            npcId = "vance",
            npcName = "Mr. Vance",
            npcRole = "Café Owner",
            initialMood = NpcMood.BUSY,
            bodyLanguageCue = "Half-distracted by invoices, tapping a pen impatiently.",
            environmentAction = "You noticed the main grinder overheating and smelling burnt. If it dies tomorrow, the café can't open. You need Mr. Vance to call repairs today.",
            npcLine = "Look, I'm buried in receipts right now. Can whatever it is wait until Monday?",
            highlightedClue = clues["clue_cant_afford"],
            subtextNote = "Every way of saying something is 'important' carries a different weight and urgency.",
            choices = listOf(
                DialogueChoice(
                    id = "d5_vance_mild",
                    text = "It's kind of important — the grinder sounds a bit weird.",
                    communicativeAct = CommunicativeAct.PRACTICAL_ASK,
                    trustDelta = 0,
                    completesCurrentBeat = true,
                    memorySummary = "Your gentle phrasing made the grinder issue sound minor to Mr. Vance.",
                    worldReactionNote = "Mr. Vance waved it off: 'Let's check it Monday then.' (You realized softer framing didn't move him!)",
                    boostSpeaking = 3, boostPragmatics = 3
                ),
                DialogueChoice(
                    id = "d5_vance_weight",
                    text = "Mr. Vance, we can't afford to ignore this — if the grinder breaks, we lose Saturday's whole rush.",
                    communicativeAct = CommunicativeAct.FRAME_URGENCY,
                    trustDelta = 3,
                    moneyDelta = 30,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_cant_afford",
                    memorySummary = "You framed the problem with real-world weight and convinced Mr. Vance to call repairs immediately (+$30 bonus).",
                    worldReactionNote = "Mr. Vance froze, set his pen down, and picked up the phone immediately: 'Good catch. Saved us a disaster — here's a $30 bonus.'",
                    boostPragmatics = 10, boostSpeaking = 8, boostVocabulary = 6
                ),
                DialogueChoice(
                    id = "d5_vance_consequence",
                    text = "This could have serious consequences for tomorrow morning if we don't fix it today.",
                    communicativeAct = CommunicativeAct.FRAME_URGENCY,
                    trustDelta = 2,
                    moneyDelta = 20,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_cant_afford",
                    memorySummary = "You clearly communicated the consequences of waiting and persuaded Mr. Vance (+$20).",
                    worldReactionNote = "Mr. Vance nodded seriously: 'Alright, show me. Calling the technician now.'",
                    boostPragmatics = 8, boostSpeaking = 7, boostFluency = 5
                )
            )
        ),

        // DAY 6: Navigating Boundaries & Idioms ("Don't push your luck")
        SceneNode(
            id = "day6_sam_boundary",
            day = 6,
            chapterTitle = "Chapter 06 · Boundaries & Repair",
            locationId = LocationId.GROCERY,
            npcId = "elena",
            npcName = "Elena & Sam",
            npcRole = "Corner Grocery",
            initialMood = NpcMood.SKEPTICAL,
            bodyLanguageCue = "Sam smiles sheepishly holding two extra pastries; Elena shoots him a sharp look over her glasses.",
            environmentAction = "Sam already got a free coffee discount today and now asks you to cover his groceries until next week too.",
            npcLine = "Elena whispers to Sam: 'Don't push your luck.' Then she glances at you — how do you want to handle Sam's request?",
            highlightedClue = clues["clue_push_luck"],
            subtextNote = "Goodwill isn't unlimited — you can be kind without letting someone cross your boundary.",
            choices = listOf(
                DialogueChoice(
                    id = "d6_boundary_warm",
                    text = "I'd love to help, Sam, but my budget is tight this week too.",
                    communicativeAct = CommunicativeAct.ASSERT_BOUNDARY,
                    trustDelta = 3,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_push_luck",
                    memorySummary = "You set a warm, honest financial boundary with Sam without creating conflict.",
                    worldReactionNote = "Sam nodded understandingly: 'Totally fair — I shouldn't have asked.' Elena smiled approvingly at your poise.",
                    boostPragmatics = 9, boostSpeaking = 7, boostComprehension = 7
                ),
                DialogueChoice(
                    id = "d6_boundary_repair",
                    text = "Sorry if I gave the wrong impression earlier — I can cover a coffee, not a full bag.",
                    communicativeAct = CommunicativeAct.REPAIR_MISUNDERSTANDING,
                    trustDelta = 2,
                    moneyDelta = -4,
                    completesCurrentBeat = true,
                    unlocksClueId = "clue_push_luck",
                    memorySummary = "You cleared up a misunderstanding gently while protecting your budget.",
                    worldReactionNote = "Sam laughed lightly: 'Fair deal! A coffee is already huge.'",
                    boostPragmatics = 8, boostAdaptability = 7
                )
            )
        ),

        // DAY 7: Week 1 Community Gathering at Harbor Park
        SceneNode(
            id = "day7_harbor_gathering",
            day = 7,
            chapterTitle = "Chapter 07 · A Life in New Haven",
            locationId = LocationId.PARK,
            npcId = "alex",
            npcName = "Alex & Neighbors",
            npcRole = "Maple St. & Harbor Community",
            initialMood = NpcMood.WARM,
            bodyLanguageCue = "String lights glow over a picnic table where Alex, Maya, Elena, and Jordan wave you over.",
            environmentAction = "Seven days ago you arrived with $50 and knew nobody. Tonight, New Haven feels different.",
            npcLine = "Hey, look who made it through week one! Tell everyone — how does New Haven feel now?",
            subtextNote = "Speak from your lived week — your relationships and choices led right to this table.",
            choices = listOf(
                DialogueChoice(
                    id = "d7_reflect_connection",
                    text = "A week ago I didn't know a soul. Now this street actually feels like home.",
                    communicativeAct = CommunicativeAct.VULNERABLE_SHARE,
                    trustDelta = 4,
                    completesCurrentBeat = true,
                    memorySummary = "At Sunday's Harbor Park gathering, you shared how New Haven grew from a strange city into home.",
                    worldReactionNote = "Everyone raised their glasses. Maya joked: 'And you survived the Saturday espresso rush too!'",
                    boostFluency = 8, boostSpeaking = 8, boostPragmatics = 8
                ),
                DialogueChoice(
                    id = "d7_reflect_adapt",
                    text = "I realized I don't need every perfect word — people meet you halfway when you try.",
                    communicativeAct = CommunicativeAct.PARAPHRASE,
                    trustDelta = 4,
                    completesCurrentBeat = true,
                    memorySummary = "You reflected on discovering that communication in English is about connection, not perfection.",
                    worldReactionNote = "Jordan raised a glass: 'That's the best line I've heard all week.'",
                    boostAdaptability = 10, boostComprehension = 8, boostSpeaking = 8
                )
            )
        )
    )

    val dayBeatOrder: Map<Int, List<String>> = mapOf(
        1 to listOf("day1_neighbor_alex", "day1_cafe_order", "day1_cafe_spill", "day1_cafe_hiring"),
        2 to listOf("day2_grocery_paraphrase", "day2_cafe_shift"),
        3 to listOf("day3_park_subtext", "day3_alex_followup"),
        4 to listOf("day4_cafe_critical", "day4_park_critical_contrast"),
        5 to listOf("day5_vance_persuade"),
        6 to listOf("day6_sam_boundary"),
        7 to listOf("day7_harbor_gathering")
    )

    fun sceneById(id: String): SceneNode =
        scenes.firstOrNull { it.id == id } ?: scenes.first()

    fun firstPendingSceneForDay(day: Int, completedIds: Set<String>): SceneNode? {
        val beats = dayBeatOrder[day] ?: return null
        val nextId = beats.firstOrNull { it !in completedIds } ?: return null
        return sceneById(nextId)
    }

    fun freeRoamSceneForLocation(locationId: LocationId, day: Int): SceneNode {
        return when (locationId) {
            LocationId.APARTMENT -> SceneNode(
                id = "roam_apartment_d$day",
                day = day,
                chapterTitle = "Maple St. · Everyday Life",
                locationId = LocationId.APARTMENT,
                npcId = "alex",
                npcName = "Alex",
                npcRole = "Neighbor in 4B",
                initialMood = NpcMood.WARM,
                bodyLanguageCue = "Checking the mailbox in the sunlit foyer.",
                environmentAction = "You bump into Alex on your way downstairs.",
                npcLine = "Hey neighbor! How's your day going so far?",
                choices = emptyList(),
                allowsFreeInput = true,
                isStoryBeat = false
            )
            LocationId.CAFE -> SceneNode(
                id = "roam_cafe_d$day",
                day = day,
                chapterTitle = "Sunbeam Café · Everyday Life",
                locationId = LocationId.CAFE,
                npcId = "maya",
                npcName = "Maya",
                npcRole = "Head Barista",
                initialMood = NpcMood.WARM,
                bodyLanguageCue = "Steaming milk behind the espresso bar.",
                environmentAction = "Warm espresso aroma fills Sunbeam Café.",
                npcLine = "Hey! Want your usual, or just stopping by to chat?",
                choices = emptyList(),
                allowsFreeInput = true,
                isStoryBeat = false
            )
            LocationId.GROCERY -> SceneNode(
                id = "roam_grocery_d$day",
                day = day,
                chapterTitle = "Corner Grocery · Everyday Life",
                locationId = LocationId.GROCERY,
                npcId = "elena",
                npcName = "Elena",
                npcRole = "Shopkeeper",
                initialMood = NpcMood.CURIOUS,
                bodyLanguageCue = "Arranging fresh apples by the front counter.",
                environmentAction = "Baskets and handwritten price signs line the cozy aisles.",
                npcLine = "Morning! Cooking anything good tonight?",
                choices = emptyList(),
                allowsFreeInput = true,
                isStoryBeat = false
            )
            LocationId.PARK -> SceneNode(
                id = "roam_park_d$day",
                day = day,
                chapterTitle = "Harbor Park · Everyday Life",
                locationId = LocationId.PARK,
                npcId = "jordan",
                npcName = "Jordan",
                npcRole = "Local Writer",
                initialMood = NpcMood.CURIOUS,
                bodyLanguageCue = "Notebook open on the park bench overlooking the harbor.",
                environmentAction = "Sea breeze rustles through the oak trees.",
                npcLine = "Take a seat! What's been the strangest or best moment of your week here?",
                choices = emptyList(),
                allowsFreeInput = true,
                isStoryBeat = false
            )
        }
    }
}
