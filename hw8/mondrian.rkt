#lang slideshow

(define (randoms seed size)
  (if (= size 0)
  '()
  ;;first element is seed / m
  (randomList seed size (list (/ seed 4294967296.0)) (/ seed 4294967296.0))
  )
)

(define (randomList seed size lst prevElem)
  (if (= (length lst) size)
  lst
  (randomList seed size (cons (/ (modulo ( exact-round (+ (* prevElem 1664525) 1013904223) ) 4294967296) 4294967296.0) lst) (modulo ( exact-round (+ (* prevElem 1664525) 1013904223) ) 4294967296))
  )
)

(define (mondrianSquare x y rands)
  (if (< x 50)
      (if (< y 50)
          ;;choose a color at random
          (if (< (first rands) .33)
              (cc-superimpose (colorize (filled-rectangle x y) "skyblue") (rectangle (+ x 2) (+ y 2)))
              (if (> (first rands) .66)
                (cc-superimpose (colorize (filled-rectangle x y) "yellow") (rectangle (+ x 2) (+ y 2)))
                (cc-superimpose (colorize (filled-rectangle x y) "red") (rectangle (+ x 2) (+ y 2)))
                  ))
          (values)
          )
      (values)
  )
)

(define (mondrian x y rands)
    mondrianHelper(x y rands '())
)

(define (mondrianHelper x y rands returnLst)
   
  (letrec ([width (list x)]
         [height (list y)]
         (wide-enough (lambda (w r) (and (> w 50) (> (/ w width) (* 0.5 r)))))
         (tall-enough (lambda (h r) (and (> h 50) (> (/ h height) (* 0.5 r)))))
         (big-enough (lambda (w r1 h r2) (and (wide-enough) (tall-enough))))
         (mondrian-helper (lambda (x y rands returnLst) '(1 2 3)))
         )
    (mondrian-helper x y rands returnLst)
  )  
  
)

;;following code is used to run eval
(define-namespace-anchor anc)
(define ns (namespace-anchor->namespace anc))

;(randoms 3023467485 40)
(mondrianSquare 40 40 '(.2))
;(define x 5)
;(define y 10)
;(define expr (mondrian 40 40 (randoms 3023467485 40)))
;(define expr '((cc-superimpose (colorize (filled-rectangle x y) "yellow") (rectangle (+ x 2) (+ y 2)))))
;(eval expr ns)
(mondrian 40 40 (randoms 3023467485 40))