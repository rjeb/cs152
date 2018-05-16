#lang racket

(define (inverseCycle lst)
  flatten
  (cons(car lst)
  (foldl cons '() (rest lst)))
)

(define (inverse lst)
  (map (lambda (elem) (inverseCycle elem)) lst)
)