%cycles([], [[]]).
%cycles([H|T], X) :- not(cycles0([H|T], X)), X = [[]].
%cycles([H|T], X) :- cycles0([H|T], X).

cycles([H|T], X) :- cycleCreate(0, 0, [H|T], TMP), sort(0, @<, TMP, TMP2), removeCycles(TMP2, X).

removeCycles([H|T], X) :- removeCycles1([H|T], TMP), X = TMP.

removeCycles1([], []).
removeCycles1([H|T], X) :- checkCycle(H), removeCycles1(T, TMP), X = TMP. 
removeCycles1([H|T], X) :- not(checkCycle(H)), removeCycles1(T, TMPLST), append(H, TMPLST, X). 

checkCycle([H|T]) :- delete([H|T], H, TMPLST), TMPLST = [].

%Test Case: cycles([[0,3,5],[0,6,3],[0,5,3],[0,3,6]], X).

cycleCreate(_, B, [H|T], X) :- length([H|T], LEN), B >= LEN, X = [].
cycleCreate(A, B, [H|T], X) :- nth0(B, [H|T], CYCLEIN), length(CYCLEIN, LEN), A =:= LEN, A1 is 0, B1 is B + 1, cycleCreate(A1, B1, [H|T], X). 

cycleCreate(INDEXNUM, INDEXLST, [H|T], X) :- nth0(INDEXLST, [H|T], CYCLEIN), nth0(INDEXNUM, CYCLEIN, NUM), cycles1(NUM, [H|T], CYCLEGEN), INCREMENT is INDEXNUM + 1, cycleCreate(INCREMENT, INDEXLST, [H|T], TMP), normalize(CYCLEGEN, CYCLEGEN2), not(member(CYCLEGEN2, TMP)), append([CYCLEGEN2], TMP, X).

cycleCreate(INDEXNUM, INDEXLST, [H|T], X) :- nth0(INDEXLST, [H|T], CYCLEIN), nth0(INDEXNUM, CYCLEIN, NUM), cycles1(NUM, [H|T], CYCLEGEN), INCREMENT is INDEXNUM + 1, cycleCreate(INCREMENT, INDEXLST, [H|T], TMP), normalize(CYCLEGEN, CYCLEGEN2), member(CYCLEGEN2, TMP), X = TMP.

normalize([H|T], [H|T]) :- min_member(H, [H|T]).
normalize([H|T], ANSW) :- not(min_member(H, [H|T])), last([H|T], LAST), delete([H|T], LAST, LST), append([LAST], LST, LST1), normalize(LST1, ANSW).

findcycle(ELEM, [H|T], X) :-findcycle1(ELEM, ELEM, [H|T], X).

findcycle1(SEARCH, ELEM, [H|T], X) :- findFinal(ELEM, [H|T], SEARCH), X = [[]].
findcycle1(SEARCH, ELEM, [H|T], X) :- not(findFinal(ELEM, [H|T], SEARCH)), findFinal(ELEM, [H|T], TMP), findcycle1(SEARCH,TMP,[H|T],TMP2), append(TMP, TMP2, X).


cycles1(ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), not(findFinal(MATCH, [H|T], ELEM)), cycles2(ELEM, MATCH, [H|T], MATCH1), append([ELEM], MATCH1, X).
%simple cycles of length 2
cycles1(ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), findFinal(MATCH, [H|T], ELEM), compare(<, ELEM, MATCH), X = [ELEM, MATCH].
cycles1(ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), findFinal(MATCH, [H|T], ELEM), compare(=, ELEM, MATCH), X = [ELEM, MATCH].
cycles1(ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), findFinal(MATCH, [H|T], ELEM), compare(>, ELEM, MATCH), X = [MATCH, ELEM].

cycles2(SEARCH, ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), not(findFinal(MATCH, [H|T], SEARCH)), cycles2(SEARCH, MATCH, [H|T], MATCH1), append([ELEM], MATCH1, X).
cycles2(SEARCH, ELEM, [H|T], X) :- findFinal(ELEM, [H|T], MATCH), findFinal(MATCH, [H|T], SEARCH), X = [ELEM, MATCH].

findFinal(ELEM, [], ELEM).
findFinal(ELEM, [H|T], X) :- findMatch(ELEM, MATCH1, H), findFinal(MATCH1, T, X).

%test case : findFinal(0, [[0, 3, 5], [0, 6, 3]], X).
%test case ; findFinal(0, [[0, 3,5], [0,6,3]], X).


% applyCycle(Cycle, A, B)
% The given cycle sends A to B
findMatch(ELEM, MATCH, LST) :-not(nth1(_, LST, ELEM)), MATCH = ELEM.
findMatch(ELEM, MATCH, LST) :- length(LST, LEN), nth1(INDEX, LST, ELEM), INDEX < LEN, nextto(ELEM, MATCH, LST).
findMatch(ELEM, MATCH, [MATCH|T]) :- length([MATCH|T], LEN), nth1(INDEX, [MATCH|T], ELEM), INDEX = LEN.

%replace([_|T], 0, X, [X|T]).
%replace([H|T], I, X, [H|R]):- I > -1, NI is I-1, replace(T, NI, X, R), !.
%replace(L, _, _, L).

%CREATED PREDICATES WITH DIFFERENT NAMES/FUNCTIONALITY FOR CYCLES BUT IMPLEMENTED SOME OF THE PREDICATES BELOW

%applyCycle([], A, A).
%applyCycle([H|T], [A], [B]):- nth0(H, A, ELEM), replace(A, H, ELEM, LST).

applyCycle(LST, IN, OUT) :- findMatch(IN, OUT, LST).

% applyPerm(Cycles, A, B)
% The given permutation (list of cycles) sends A to B

applyPerm(LST, IN, OUT) :- findFinal(IN, LST, OUT).

% orbit(Perm, A, Orbit)
% Repeatedly applying Perm to A sends A to all elements in Orbit

orbit(Perm, A, Orbit) :- cycles1(A, Perm, Orbit).

rotation(r0, [1, 4, 2], [d, l, f]).
rotation(r1, [0, 3, 5], [d, f, r]).
rotation(r2, [0, 6, 3], [f, l, u]).
rotation(r3, [1, 2, 7], [f, u, r]).
rotation(r4, [0, 6, 5], [b, d, l]).
rotation(r5, [1, 4, 7], [b, r, d]).
rotation(r6, [2, 7, 4], [b, l, u]).
rotation(r7, [3, 5, 6], [b, u, r]).

name(R, N) :- rotation(N, R, _).
name(R, N) :- rotation(N, _, R).

% addFront1(E, Ts, Result): Puts E in front of all elements of Ts
addFront1(_, [], []).
addFront1(E, [T|Ts], [[E|T]|Rs]) :- addFront1(E, Ts, Rs).

% addFront2(S, Ts, Result): Puts all elements of S in front of all
% elements of Ts
addFront2([], _, []).
addFront2([H|T], Ts, Us) :- addFront1(H, Ts, R1), addFront2(T, Ts, R2), append(R1, R2, Us).

picks(_, 0, [[]]).
picks(S, N, R) :- N > 0, N1 is N - 1, picks(S, N1, R1), addFront2(S, R1, R).

find(Perms, Target, Pick, N) :- picks(Perms, N, Picks), member(Pick, Picks), cycles(Pick, Target).

%findall(X, rotation(_, _, X), VertexRotations), find(VertexRotations, [], S,  3).
