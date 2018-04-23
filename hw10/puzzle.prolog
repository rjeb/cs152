% flipped(Click, Columns, Cells)
% Cells is the list of cells that are flipped by a click at index
% Click in a row of the given number of columns.

flipped(Click, Columns, [Left, Click, Right]) :- Click > 1, Click < Columns, Left is Click - 1, Right is Click + 1.
flipped(Click, Columns, [Left, Click]) :- Click > 1, Click = Columns, Left is Click - 1.
flipped(Click, Columns, [Click, Right]) :- Click = 1, Click < Columns, Right is Click + 1.
flipped(Click, Columns, [Click]) :- Columns = 1.

%IMPLEMENT THE BELOW PREDICATES
% doFlips(Cells, Row, Columns, Result)
% Result is a list of red positions resulting from flipping all
% Cells (list of positions) in a Row (list of red positions)
% of the given number of columns.

doFlips(Cells, Row, Colummns, Result) :- .

% allFlips(Flips, Row, Rows, Columns, AllFlips)
% yields in AllFlips a list of lists for flips for each row so that the
% initial set of Flips in the initial Row turns the rectangle
% of the given rows and columns all red.

allFlips(Flips, Row, Rows, Columns, [Flips | MoreFlips]) :-
    Rows > 0,
    doFlips(...),
    numlist(1, Columns, All),
    ord_symdiff(...),
    Rows1 is Rows - 1,
    allFlips(...).
allFlips([], _, 0, _, []).

solution(Rows, Columns, Solution) :- numlist(1, Columns, L), sublist(Solution, L), allFlips(Solution, [], Rows, Columns, _).
