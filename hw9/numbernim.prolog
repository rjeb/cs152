move(X,Y) :- Z is X/2, Z1 is truncate(Z), between(1, Z1, Z2), Z3 is X - Z2, Y is truncate(Z3).

win(X) :- X=:=2.
win(X) :- move(X,Y), not(win(Y)).

