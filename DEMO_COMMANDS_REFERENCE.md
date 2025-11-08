# TrackerGuru Demo Commands Reference

**Quick reference for copy-paste commands during presentation.**

---

## 📋 Pre-Demo Setup Commands

Run these commands to prepare the demo environment:

```bash
# 1. Clear existing data
clear

# 2. Create all required tag groups
tg location
tg budget
tg rooms
tg urgent
tg viewing

# 3. Add contact 1: David Chen (Seller - 3-room AMK)
add n/David Chen p/98765432 e/david@email.com a/Blk 456 Ang Mo Kio Ave 3 #12-34 r/seller t/rooms.3 t/location.amk t/budget.400k

# 4. Add contact 2: Mary Wong (Buyer - Low urgency, 3-room Bedok)
add n/Mary Wong p/87654321 e/mary@email.com a/Blk 789 Bedok North St 2 #08-11 r/buyer t/rooms.3 t/location.bedok t/budget.300k t/urgent.low

# 5. Add contact 3: James Tan (Seller - 4-room Jurong)
add n/James Tan p/91112233 e/james@email.com a/Blk 234 Jurong West St 52 #06-18 r/seller t/rooms.4 t/location.jurong t/budget.550k

# 6. Add contact 4: Linda Lee (Seller - PERFECT PROPERTY FOR SARAH!)
add n/Linda Lee p/82223344 e/linda@email.com a/Blk 88 Tampines Ave 10 #10-25 r/seller t/rooms.4 t/location.tampines t/budget.500k

# 7. Add contact 5: Robert Ng (Seller - 5-room Yishun)
add n/Robert Ng p/93334455 e/robert@email.com a/Blk 102 Yishun Ring Rd #03-07 r/seller t/rooms.5 t/location.yishun t/budget.650k

# 8. Add contact 6: Michelle Koh (Buyer - Low urgency, 3-room Pasir Ris)
add n/Michelle Koh p/84445566 e/michelle@email.com a/Blk 567 Pasir Ris St 51 #12-88 r/buyer t/rooms.3 t/location.pasirris t/budget.350k t/urgent.low

# 9. Verify setup (should show 6 contacts)
list
```

**Expected Result:** 6 contacts in system with diverse tags

---

## ✅ Pre-Presentation Verification Checklist

Before starting your demo, verify:

### Technical Setup
- [ ] On `filter-by-tag` branch: `git branch --show-current`
- [ ] Application built: `./gradlew clean shadowJar`
- [ ] Application running: `java -jar build/libs/trackerguru.jar`
- [ ] Data cleared: Run `clear` command

### Tag Groups Created
- [ ] All 5 tag groups created (location, budget, rooms, urgent, viewing)
- [ ] Verify: Try adding a test tag like `t/urgent.test` - should work without error

### Contacts Added
- [ ] 6 contacts added successfully
- [ ] Verify: Run `list` - should show exactly 6 contacts
- [ ] Linda Lee is contact #4 (seller) with tags: rooms.4, location.tampines, budget.500k

### Commands Ready
- [ ] DEMO_COMMANDS_REFERENCE.md open on second screen
- [ ] DEMO_SCRIPT.md open for narration reference
- [ ] Commands tested once end-to-end

### Edit Command Understanding
- [ ] Understand edit is NOT cumulative
- [ ] Must re-specify ALL tags to preserve them
- [ ] Status field uses `s/PENDING` or `s/COMPLETED`


---

## 🎯 Main Demo Commands (In Order)

### Step 1: Add Sarah (The Hot Lead)
```bash
add n/Sarah Tan p/91234567 e/sarah.tan@email.com a/Blk 123 Tampines St 11 #05-67 r/buyer t/urgent.high t/budget.500k t/location.tampines t/rooms.4
```
**Expected:** Sarah added as contact #7  
**Verify:** Run `list` - should show 7 contacts

---

### Step 2: Filter Urgent Leads
```bash
filter t/urgent.high
```
**Expected:** Shows ONLY Sarah (1 contact)  
**Talking Point:** "Out of 7 contacts, only ONE has urgent.high. That's precision."

---

### Step 3: Find Matching Properties
```bash
filter t/rooms.4 t/location.tampines t/budget.500k
```
**Expected:** Shows Linda (#1) AND Sarah (#2) - both have ALL 3 tags  
**Why:** Linda is seller with rooms.4 property, Sarah is buyer seeking rooms.4. James has rooms.4 but in Jurong. Others don't have all 3 tags.  
**Talking Point:** "Linda HAS what Sarah WANTS! A 4-room in Tampines at $500k. TrackerGuru just matched buyer and seller instantly."

**Note:** Multiple tags use AND logic - contact must have ALL specified tags.  
**CRITICAL:** Stay on this filtered view! Linda is #1, Sarah is #2. All subsequent edits use these numbers!

---

### Step 4: Update Progress - Viewing Scheduled

**Update Linda first:**
```bash
edit 1 s/PENDING t/rooms.4 t/location.tampines t/budget.500k t/viewing.scheduled
```
**Expected:** Linda (contact #1 in filtered view) updated with status PENDING and viewing.scheduled tag  
**Note:** Edit returns to full list

**Filter back to matched pair:**
```bash
filter t/rooms.4 t/location.tampines t/budget.500k
```

**Update Sarah:**
```bash
edit 2 s/PENDING t/urgent.high t/budget.500k t/location.tampines t/rooms.4 t/viewing.scheduled
```
**Expected:** Sarah (contact #2) updated with viewing.scheduled tag, status set to PENDING  
**Talking Point:** "Both sides tracked. No forms. No menus. Just typing and hitting enter."

---

### Step 5: Closing the Deal

**Filter back to matched pair:**
```bash
filter t/rooms.4 t/location.tampines t/budget.500k
```

**Close Linda's deal:**
```bash
edit 1 s/COMPLETED
```
**Expected:** Linda's status changed to COMPLETED  
**Note:** Only status changes - tags remain the same  
**Talking Point:** "Linda's property: SOLD. Status COMPLETED."

**Filter back:**
```bash
filter t/rooms.4 t/location.tampines t/budget.500k
```

**Close Sarah's deal:**
```bash
edit 2 s/COMPLETED
```
**Expected:** Sarah's status changed to COMPLETED  
**Talking Point:** "Sarah's deal: COMPLETED. Two contacts. Two weeks. Zero leads lost."

---

### Step 6: Show Final Result
```bash
list
```
**Expected:** Shows all 7 contacts with both Linda and Sarah marked as COMPLETED

## 🎁 Bonus Demonstrations (If Time Permits)

### Show All Buyers
```bash
filter r/buyer
```
**Expected:** 3 contacts (Sarah, Mary, Michelle)  
**Talking Point:** "Need to see all buyers? One command."

---

### Show All 4-Room Properties
```bash
filter t/rooms.4
```
**Expected:** 3 contacts (James, Linda, Sarah)  
**Talking Point:** "All 4-room related contacts instantly."

---

### Show All Tampines Contacts
```bash
filter t/location.tampines
```
**Expected:** 2 contacts (Sarah - buyer, Linda - seller with property)  
**Talking Point:** "Geographic targeting—buyer and seller in the same area. That's a deal waiting to happen."

---

### Show All $500k Budget Contacts
```bash
filter t/budget.500k
```
**Expected:** 2 contacts (Sarah, Linda)  
**Talking Point:** "Sarah's budget matches Linda's listing price exactly. Perfect alignment."

---

### Filter by Status
```bash
filter s/COMPLETED
```
**Expected:** Shows Sarah and Linda (both completed deals)  
**Talking Point:** "All closed deals at a glance."

---

### Return to Full List
```bash
list
```
**Talking Point:** "Any filter. Any time. Always one command away from your full contact base."

---

## ⚠️ Critical Reminders

### Tag Filtering Logic
- **Multiple tags use AND logic** - Contact must have ALL specified tags
- Example: `filter t/listing.4room t/location.tampines t/budget.500k`
  - Shows contacts with listing.4room AND location.tampines AND budget.500k
  - Does NOT show contacts with only 1 or 2 of these tags

### Edit Command Behavior
- **NOT cumulative** - Must re-specify ALL tags
- **Status field** - Use `s/PENDING` or `s/COMPLETED`
- **Tag preservation** - Include every tag you want to keep

### Tag Evolution Through Demo

**Sarah (Buyer - Contact #7):**
| Step | Status | Tags Count | Key Change |
|------|--------|------------|------------|
| Add | none | 4 | Initial tags |
| Viewing | PENDING | 5 | +viewing.scheduled |
| Offer | PENDING | 6 | +offer.made |
| Close | COMPLETED | 6 | -urgent.high, +deal.closed |

**Linda (Seller - Contact #4):**
| Step | Status | Tags Count | Key Change |
|------|--------|------------|------------|
| Setup | none | 3 | Initial tags |
| Viewing | PENDING | 4 | +viewing.scheduled |
| Offer | PENDING | 5 | +offer.received |
| Close | COMPLETED | 5 | +deal.closed |

### Expected Filter Counts
| Command | Count | Names |
|---------|-------|-------|
| `list` | 7 | All contacts |
| `filter t/urgent.high` | 1 | Sarah only |
| `filter t/rooms.4 t/location.tampines t/budget.500k` | 2 | Linda (#1 seller), Sarah (#2 buyer) |
| `filter r/buyer` | 3 | Sarah, Mary, Michelle |
| `filter t/location.tampines` | 2 | Sarah (buyer), Linda (seller) |

---

## 🚨 Emergency Recovery

### If You Lose Tags During Edit
```bash
# Immediately re-add all tags:
edit 1 s/PENDING t/urgent.high t/budget.500k t/location.tampines t/rooms.4 t/viewing.scheduled
```

### If Filter Shows Unexpected Results
```bash
list  # Show all contacts to verify data
```

### If Tag Group Error Occurs
```bash
# Create missing tag group:
tagG [groupname]
```

---

## 📊 Quick Verification During Demo

After each major step, glance at contact count:
- **After pre-setup:** 6 contacts ✅
- **After adding Sarah:** 7 contacts ✅
- **After urgent filter:** 1 contact shown ✅
- **After criteria filter:** 2 contacts shown ✅
- **After each edit:** Verify tag count in display

---

## 🎯 Success Metrics

Your demo is successful when evaluators:
- ✅ Understand the pain point (lost $500K deal)
- ✅ See TrackerGuru as the solution (intelligent filtering)
- ✅ Feel the value (time saved = commissions protected)
- ✅ Remember the tagline ("Track smarter. Close faster. Win more.")
- ✅ Recognize the "Linda discovery" as intelligence, not just filtering

---

**You're ready to showcase TrackerGuru! 🚀**

