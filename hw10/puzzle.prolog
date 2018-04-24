% flipped(Click, Columns, Cells)
% Cells is the list of cells that are flipped by a click at index
% Click in a row of the given number of columns.

flipped(Click, Columns, [Left, Click, Right]) :- Click > 1, Click < Columns, Left is Click - 1, Right is Click + 1.
flipped(Click, Columns, [Left, Click]) :- Click > 1, Click = Columns, Left is Click - 1.
flipped(Click, Columns, [Click, Right]) :- Click = 1, Click < Columns, Right is Click + 1.
flipped(Click, Columns, [Click]) :- Columns = 1.

contains(H, [H|_]).
contains(H, [_|T2]) :- contains(H, T2).

xor(X, Y, X) :- X > 0, Y =:= 0.
xor(X, Y, Y) :- Y > 0, X =:= 0.
xor(X, Y, Z) :- Y > 0, X > 0, Z is 0.
xor(X, Y, Z) :- Y = 0, X = 0, Z = 0.

xorList(X, Y, ANS):- intersection(X,Y, TMP), subtract(X, TMP, X1), subtract(Y, TMP, Y1), append(X1, Y1, ANS).

%IMPLEMENT THE BELOW PREDICATES
% doFlips(Cells, Row, Columns, Result)
% Result is a list of red positions resulting from flipping all
% Cells (list of positions) in a Row (list of red positions)
% of the given number of columns.

%doFlips([H|T], ROW, COL, RES) :- flipped(H, COL, RES).

%calcFlip([], _, _).
%calcFlip([H|T], COL, RES) :- flipped(H, COL, RES1), calcFlip(T,COL,RES2), xorList(RES1, RES2).

% allFlips(Flips, Row, Rows, Columns, AllFlips)
% yields in AllFlips a list of lists for flips for each row so that the
% initial set of Flips in the initial Row turns the rectangle
% of the given rows and columns all red.

%allFlips(Flips, Row, Rows, Columns, [Flips | MoreFlips]) :-
%    Rows > 0,
%    doFlips(...),
%    numlist(1, Columns, All),
%    ord_symdiff(...),
%    Rows1 is Rows - 1,
%    allFlips(...).
%allFlips([], _, 0, _, []).

solution(Rows, Columns, Solution) :- numlist(1, Columns, L), sublist(Solution, L), allFlips(Solution, [], Rows, Columns, _).
