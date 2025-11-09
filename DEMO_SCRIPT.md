# TrackerGuru Live Demo Script: Xiao Ming & Sarah

## Branch Information
**Branch:** `filter-by-tag` (Demo branch with enhanced tag filtering capability)

**Note:** This demo showcases the ability to filter by specific tags (e.g., `budget.500k`, `urgent.high`), which is implemented in this branch but not in the main branch due to feature freeze constraints.

---

## Pre-Demo Setup

### 1. Start with a clean slate
```bash
# Delete existing data file to start fresh
clear
```

### 2. Create Tag Groups (Required for tag organization)

TrackerGuru uses tag groups to organize tags. Create all required tag groups first:

```bash
# Create tag groups for the demo
tg location
tg budget
tg rooms
tg urgent
tg viewing
```

**Why these tag groups:**
- `location` - For geographic areas (location.amk, location.tampines, etc.)
- `budget` - For price points (budget.300k, budget.500k, etc.)
- `rooms` - For room specifications (rooms.3, rooms.4, etc.) - **Used by BOTH buyers and sellers**
- `urgent` - For priority levels (urgent.low, urgent.medium, urgent.high)
- `viewing` - For viewing milestones (viewing.scheduled)

### 3. Add realistic sample contacts (for context - shows Xiao Ming's existing workload)

These contacts are strategically designed to showcase filtering capabilities:
- **Varied urgency levels** (high, medium, low) - to show priority filtering
- **Multiple locations** - to show geographic filtering
- **Different budgets** - to show price range filtering
- **Different room types** - to show specification matching
- **Mix of buyers and sellers** - to show role filtering

```bash
# Contact 1: Seller with 3-room in AMK
add n/David Chen p/98765432 e/david@email.com a/Blk 456 Ang Mo Kio Ave 3 #12-34 r/seller t/rooms.3 t/location.amk t/budget.400k

# Contact 2: Low-urgency buyer looking for 3-room in Bedok
add n/Mary Wong p/87654321 e/mary@email.com a/Blk 789 Bedok North St 2 #08-11 r/buyer t/rooms.3 t/location.bedok t/budget.300k t/urgent.low

# Contact 3: Seller with 4-room in Jurong (higher budget)
add n/James Tan p/91112233 e/james@email.com a/Blk 234 Jurong West St 52 #06-18 r/seller t/rooms.4 t/location.jurong t/budget.550k

# Contact 4: Seller with PERFECT PROPERTY for Sarah!
add n/Linda Lee p/82223344 e/linda@email.com a/Blk 88 Tampines Ave 10 #10-25 r/seller t/rooms.4 t/location.tampines t/budget.500k

# Contact 5: Seller with 5-room in Yishun (premium)
add n/Robert Ng p/93334455 e/robert@email.com a/Blk 102 Yishun Ring Rd #03-07 r/seller t/rooms.5 t/location.yishun t/budget.650k

# Contact 6: Low-urgency buyer for 3-room in Pasir Ris
add n/Michelle Koh p/84445566 e/michelle@email.com a/Blk 567 Pasir Ris St 51 #12-88 r/buyer t/rooms.3 t/location.pasirris t/budget.350k t/urgent.low
```

**Why these contacts matter:**
- Linda Lee is a **SELLER with the PERFECT property** for Sarah (4-room, Tampines, $500k)
- This creates an immediate viewing opportunity and potential deal
- Varied urgency levels show that Sarah's `urgent.high` stands out
- Multiple locations demonstrate precise geographic filtering
- Mix of buyers/sellers shows real workload complexity

---

## Demo Script

### Introduction (Hook)

**[Narrator]:**
"Meet Xiao Ming, a property agent juggling 30 clients a day. Yesterday, he missed a follow-up with a hot lead—and lost a $500,000 deal. Sound familiar?"

**[Pause for 2 seconds]**

"But today, everything changes. He meets Sarah—a buyer who needs a 4-room HDB in Tampines within 2 weeks. Let's watch how TrackerGuru ensures Sarah never slips away."

**[Pause]**

"Note that in this demo we will be copying and pasting commands, in the interest of time."

---

### Step 1: Adding Sarah as a Contact

**[Narrator]:**
"Sarah just called. While she's still on the phone, Xiao Ming captures every detail in seconds—no switching between apps, no sticky notes."

**[Xiao Ming types]:**
```
add n/Sarah Tan p/91234567 e/sarah.tan@email.com a/Blk 123 Tampines St 11 #05-67 r/buyer t/urgent.high t/budget.500k t/location.tampines t/rooms.4
```

**[Result shows Sarah added to the list]**

**[Narrator]:**
"Seven fields, one command. Notice how TrackerGuru uses **tag groups**, which allow every tag to become a filter key. In the time it takes to open Excel, Xiao Ming has already captured everything that matters."

---

### Step 2: Prioritizing Sarah

**[Narrator]:**
"Given the tight deadline, Xiao Ming needs to instantly identify his highest priorities."

**[Xiao Ming types]:**
```
filter t/urgent.high
```

**[Screen shows filtered list with ONLY Sarah]**

**[Narrator]:**
"With one command, TrackerGuru surfaces all high-urgency leads, and Sarah's right at the top."

---

### Step 3: Finding Suitable Listings - The Perfect Match

**[Narrator]:**
"Now, the power move. Sarah needs a 4-room HDB in Tampines under $500k. But Xiao Ming has so many contacts in his system."

**[Pause]**

"But watch this."

**[Xiao Ming types]:**
```
filter t/rooms.4 t/location.tampines t/budget.500k
```

**[Screen shows Linda (#1) and Sarah (#2)]**

**[Narrator]:**
"Two matches appear: Linda Lee and Sarah Tan! Linda is a SELLER while Sarah is a BUYER looking for exactly the same thing. This isn't just a match—this is **a deal waiting to happen**."

**[Pause for effect]**

**[Narrator]:**
"Traditionally, Xiao Ming had to cross-reference each contact manually, hoping he didn't miss anyone. But TrackerGuru found this match in seconds. In the time it takes other agents to open their spreadsheet, Xiao Ming is already texting Sarah to arrange for a viewing!"

**[Pause]**

"And now, let's watch as Xiao Ming tracks this deal from both sides."

---

### Step 4: Updating Progress - Viewing Scheduled

**[Narrator]:**
"A few days later, Sarah views Linda's property. Xiao Ming updates BOTH contacts. We're still on the filtered view, so Linda is #1, Sarah is #2."

**[Xiao Ming types]:**
```
edit 1 s/PENDING t/rooms.4 t/location.tampines t/budget.500k t/viewing.scheduled
```

**[Result shows Linda updated]**

**[Xiao Ming types]:**
```
edit 2 s/PENDING t/urgent.high t/budget.500k t/location.tampines t/rooms.4 t/viewing.scheduled
```

**[Result shows Sarah updated with viewing tag]**

**[Narrator]:**
"No forms. No menus. Just typing and hitting enter. We can now see the progress of Linda and Sarah. The tags build a complete timeline with both sides of the transaction documented."

---

### Step 5: Closing the Deal

**[Narrator]:**
"One week in, Sarah finally makes an offer on Linda's property. Xiao Ming closes the deal for both parties, all within the app itself."

**[Xiao Ming types]:**
```
edit 1 s/COMPLETED
```

**[Result shows Linda with COMPLETED status]**

**[Xiao Ming types]:**
```
edit 2 s/COMPLETED
```

**[Result shows Sarah with COMPLETED status]**

**[Narrator]:**
"TrackerGuru didn't just manage a contact. It turned chaos into clarity, and transformed Xiao Ming from being overwhelmed to being in control."

**[Pause for 2 seconds]**

"Because in property, the agent who responds fastest wins. And with TrackerGuru, **you're always fastest**."

---

## Tips for Delivery

### Pacing
- **Speak slowly** during commands (let audience read)
- **Pause** after key rhetorical points (2-3 seconds)
- **Speed up** during transitions to maintain energy

### Emphasis
- **Bold words** in script = louder/slower delivery
- Use vocal inflection on numbers ("$500,000")
- Emphasize contrasts and key moments

### Screen Management
- Keep window maximized and font large
- Use a **clean data file** (only demo contacts)
- Have commands ready in a text file for quick copy-paste

### Backup Plan
- If a command fails, have the correct syntax visible
- Practice the demo flow 3-5 times to build muscle memory
- Keep a printed copy of commands as reference

---

## Success Metrics

After this demo, evaluators should understand:
1. ✅ **The Problem**: Agents lose deals due to poor contact management
2. ✅ **The Solution**: TrackerGuru's intelligent tagging and filtering
3. ✅ **The Value**: Time saved = commissions protected
4. ✅ **The Differentiator**: Speed and precision vs traditional CRMs

**Target Emotional Response**: "I need this tool. It would solve my exact problem."


