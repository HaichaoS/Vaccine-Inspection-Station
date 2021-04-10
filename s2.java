const UNOCCUPIED = 0
const OCCUPIED = 1

const NO_VIAL = 0
const DEF_VIAL = 1
const UNDEF_VIAL = 2
const DEFINS_VIAL = 3

range State = NO_VIAL..DEFINS_VIAL
range Occupy = UNOCCUPIED..OCCUPIED

PRODUCER = ( insert -> PRODUCER ).

CONSUMER = ( remove -> CONSUMER ).

ADDCONSUMER = ( remove -> ADDCONSUMER ).

SCANNER = ( scan -> SCANNER ).

CAROUSEL = CAROUSEL[NO_VIAL][NO_VIAL][NO_VIAL][NO_VIAL][NO_VIAL],
CAROUSEL[s1:State][s2:State][s3:State][s4:State][s5:State] = 
(
	
	when (s1 == NO_VIAL) insert -> 
		(
		defective_vial -> CAROUSEL[DEF_VIAL][s2][s3][s4][s5]
		|undective_vial -> CAROUSEL[UNDEF_VIAL][s2][s3][s4][s5]
		)
	|when(s5 != NO_VIAL) remove -> CAROUSEL[s1][s2][s3][s4][NO_VIAL]
	|when((s1 != NO_VIAL || s2 != NO_VIAL || s4 != NO_VIAL) && s3 == NO_VIAL && s5 == NO_VIAL) rotate -> CAROUSEL[NO_VIAL][s1][s2][s3][s4]
	|when(s3 != NO_VIAL) scan ->
		(
		when(s3 == DEF_VIAL) puton -> CAROUSEL[s1][s2][NO_VIAL][s4][s5]
		|when(s3 != DEF_VIAL) rotate -> CAROUSEL[NO_VIAL][s1][s2][s3][s4]
		)
).

ADDCAROUSEL = ADDCAROUSEL[NO_VIAL][NO_VIAL],
ADDCAROUSEL[s1:State][s2:State] = 
(

	when (s1 == NO_VIAL) getto -> ADDCAROUSEL[DEFINS_VIAL][s2]
	|when (s2 != NO_VIAL) remove -> ADDCAROUSEL[s1][NO_VIAL]
	|when((s1 != NO_VIAL) && (s2 == NO_VIAL)) rotate -> ADDCAROUSEL[NO_VIAL][s1]
).

SHUTTLE = SHUT[UNOCCUPIED],
SHUT[i:Occupy] = 
(
	when(i == UNOCCUPIED) puton -> set_shuttle_occupied -> putto -> SHUT[OCCUPIED]
	|when(i == OCCUPIED) set_shuttle_unoccupied -> SHUT[UNOCCUPIED]
).

ADDSHUTTTLE = ADDSHUT[UNOCCUPIED],
ADDSHUT[i:Occupy] = 
(
	when(i == UNOCCUPIED) geton -> set_addshuttle_occupied -> getto -> ADDSHUT[OCCUPIED]
	|when(i == OCCUPIED) set_addshuttle_unoccupied -> ADDSHUT[UNOCCUPIED]
).

INSPECTIONBAY = INSP[UNOCCUPIED],
INSP[i:Occupy] = 
(
	when(i == UNOCCUPIED) putto -> set_inspection_occupied -> addInspectedTagged  -> geton -> INSP[OCCUPIED]
	|when(i == OCCUPIED) set_inspection_unoccupied -> INSP[UNOCCUPIED]
).

||SIM = ( PRODUCER || CONSUMER || CAROUSEL || SCANNER || SHUTTLE || INSPECTIONBAY || ADDSHUTTTLE || ADDCAROUSEL || ADDCONSUMER).

property SAFE_PUT = ( puton -> set_shuttle_occupied -> putto -> SAFE_PUT ).
property SAFE_GET = ( geton -> set_addshuttle_occupied -> getto -> SAFE_GET ).
property SAFE_INSP = ( putto -> set_inspection_occupied -> addInspectedTagged -> geton -> SAFE_INSP ).

||SAFE_CHECK = (SIM || SAFE_PUT || SAFE_GET || SAFE_INSP ).