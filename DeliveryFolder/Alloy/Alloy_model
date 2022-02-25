//VARIABLES
//Boolean variables
abstract sig Bool{}
one sig True extends Bool{}
one sig False extends Bool{}

//Temporal variables
sig Date{
	number: one Int,
	month: one Int,
	year: one Int
	} {
	number>0
	month>0
	year>0
}
sig Time{
	hours: one Int,
	minutes: one Int,
	seconds: one Int
	} {
	hours>=0
	minutes>=0
	seconds>=0
}

//Users
abstract sig User
{
	id: Int,
	name: lone String,
	surname: lone String,
	views: one Ranking
	}{
	id>0
}
sig PolicyMaker extends User{}
sig Farmer extends User
{
	username: lone String,
	owns: one Farm,
	askHelp: lone Agronomist
}
sig Agronomist extends User
{
	manages: one Province
}

//Area
sig Province{}

//Farm data
sig Farm
{
	id: Int,
	score: Int,
	positionRanking: Int,
	waterAmount: Int,	
	telephoneNumber: Int,
	assigned: one Agronomist,	
	partecipate: one Ranking,
	located: one Province
	}{
	id>0
	score>0
	positionRanking>0
	waterAmount>=0
	telephoneNumber>911099999999 //numbers in india with area code 
	
}
sig Land
{
	id: Int,
	belongs: one Farm,
	dimensions: Int,
	humidity: Int,
	empty:one Bool,
	host: lone Product,
	crop: set Product
	}{
	id>0
	dimensions>0
	humidity>0	
}
sig Product
{
	id: Int,
	name: lone String,
	storage: one Farm,
	growingTime: Int
	}{
	id>0
	growingTime>1 //at least a month to grow
}

//Report
sig Report
{
	id: Int,
	desciption: lone String,
	advise: lone String,
	author: one Agronomist,
	farm: one Farm,
	date: one Date,
	time: one Time,
	}{
	id>0
}

//Forum
sig Discussion
{
	creator: one Farmer,
	id: Int,
	title: lone String,
	description: lone String,
	date: one Date,
	time: one Time,
	}{
	id>0
}
sig Comment
{
	id:Int,
	text: lone String,
	author: one Farmer,
	topic: one Discussion,
	date: one Date,
	time: one Time,
	}{
	id>0
}

//Ranking
one sig Ranking{}

//CONSTRAINT

//unique id constraint
fact uniqueIDDiscussion{
all disj d, d': Discussion | d.id != d'.id
}
fact uniqueIDComment{
all disj c, c': Comment | c.id != c'.id
}
fact uniqueIDUser{
all disj u, u': User | u.id != u'.id
}
fact uniqueIDFarm{
all disj f, f': Farm | f.id != f'.id
}
fact uniqueIDReport{
all disj r, r': Report | r.id != r'.id
}
fact uniqueIDLand{
all disj l, l': Land | l.id != l'.id
}
fact uniqueIDProduct{
all disj p, p': Product | p.id != p'.id
}

//Ranking
//two farms cannot be in the same position
fact farmRankingPosition{
all disj f, f': Farm | f.positionRanking != f'.positionRanking
}

//one farm is above another if it has a higher score
fact farmRankingPositionScore{
all disj f, f': Farm | (f.positionRanking < f'.positionRanking) iff (f.score >= f'.score)
}

//Farm
//a farm for every farmer
fact oneFarmerFarm{
all f:Farm | one f.~owns
}

//waterAmount = 0 iff all lands are empty, only water consumption for agriculture is considered
fact noWaterAmount{
all f:Farm, l:Land |( l.belongs = f and f.waterAmount = 0) implies l.empty = True
}

//a field is empty if it is not "hosting" products
fact emptyLand{
all l:Land |
(l.empty = True implies no l.host)
and 
(l.empty = False implies one l.host)
}

//each farm has at least 1 land
fact oneFarmLand{
all f: Farm| some l:Land | l.belongs = f
}

//Agronomist
//each agronomist manages a different province
fact oneProvinceAgronomist{
all disj a, a': Agronomist | a.manages != a'.manages
}

//each agronomist has at least 1 farm
fact oneFarmAgronomist{
all  a: Agronomist | some f: Farm | f.assigned = a
}

//if a report is linked to a farm and agronomist, the same agronomist is assigned to the farm
fact reportConnection{
all r: Report, f:Farm, a: Agronomist | (r.author = a and r.farm = f) implies (f.assigned = a)
}

//a farmer ask for help to the agronomist assigned to his farm
fact farmerAgronomistFarmConnection{
all f: Farmer, a: f.askHelp | f.owns.assigned = a
}

//for every request for help there is a report
fact askHelpReportConnection{
all a: Farmer.askHelp | some r: Report | r.author = a
}

//if a farm is located in a province and is linked to an agronomist, that agronomist manages that province
fact farmConnection{
all p: Province, f:Farm, a: Agronomist | (f.assigned = a and f.located = p) implies (a.manages = p)
}

//Province
//each province has 1 agronomist
fact oneFarmAgronomist{
all  p: Province | one a: Agronomist | a.manages = p
}

//ASSERTIONS

//Forum
//there are no two farmers who have created the same discussion
assert oneDiscussionCreator{
no disj f,f': Farmer, d: Discussion | d.creator = f and d.creator = f'
}
check oneDiscussionCreator

//there are no two farmers who have created the same comment
assert oneCommentAuthor{
no disj f,f': Farmer, c: Comment | c.author = f and c.author = f'
}
check oneCommentAuthor

//a comment cannot be related to two discussions
assert oneCommentDiscussion{
no disj d,d': Discussion, c: Comment | c.topic = d and  c.topic = d'
}
check oneCommentDiscussion

//Farm
//there are no two farmers who have the same farm
assert oneFarmFarmer{
no disj f,f': Farmer, fa: Farm | f.owns = fa and f'.owns = fa
}
check oneFarmFarmer

//there are no two farms owned by a farmer
assert oneFarmerFarm{
no disj f,f': Farm, fa: Farmer | fa.owns = f and fa.owns = f'
}
check oneFarmerFarm

//a farm cannot be located in two provinces
assert oneProvinceFarm{
no disj p,p': Province, f: Farm | f.located = p and f.located = p'
}
check oneProvinceFarm

//there are no two agronomists assigned to the same farm
assert oneAgronomistFarm{
no disj a,a': Agronomist, f: Farm | f.assigned = a and f.assigned = a'
}
check oneAgronomistFarm

//there are no two farms with the same agronomist located in different provinces
assert provinceFarmAgronomistConnection{
no disj  p,p': Province, f,f': Farm, a:Agronomist | f.assigned = a and f'.assigned = a and f.located = p and f'.located = p'
}
check provinceFarmAgronomistConnection

//a land cannot belong to two different farms
assert oneFarmLand{
no disj f,f': Farm, l: Land | l.belongs = f and l.belongs = f'
}
check oneFarmLand

//Report
//there are no two reports written by different agronomists referring to the same farm
assert agronomistReportFarmConnection{
no disj  a,a': Agronomist, r,r': Report, f:Farm | r.author = a and r'.author = a' and r.farm = f and r'.farm = f
}
check agronomistReportFarmConnection

//there are no ignored askHelp
assert askHelpIgnored{
no a: Farmer.askHelp, r: Report | no r.author -> a
}
check askHelpIgnored

//Ranking
//there are no two farms that, despite having the same score, are in the same position
assert oneRankingPosition{
no disj f,f': Farm | f.score = f'.score and f.positionRanking = f'.positionRanking
}
check oneRankingPosition

//PREDICATES

pred showRanking
{
#Ranking=1
#Farm=2
#Farmer.askHelp=0
#Report=0
#Discussion=0
#Agronomist.views=1
}
run showRanking

pred showAskHelp
{
#Farmer=2
#Farmer.askHelp=1
#Discussion=0
#Report=3
}
run showAskHelp

pred showReports
{
#Report=3
#Discussion=0
}
run showReports

pred showForum
{
#Farmer=2
#Discussion=2
#Comment=3
#Report=0
}
run showForum

pred showGeneral1{
#Farm=2
#Land=3
#Discussion=1
}
run showGeneral1

pred showGeneral2{
#Farm=2
#Land=3
#Report=3
}
run showGeneral2
