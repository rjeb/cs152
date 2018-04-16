move(X,Y) :- (X > Y).

win(X) :- X==2.
win(X) :- move(X,Y), not(win(Y)).

