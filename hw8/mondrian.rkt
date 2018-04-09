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

;;single function for creating a square
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
  (letrec ((width x)
         (height y)
         (wide-enough (lambda (w r) (and (> w 50) (> (/ w width) (* 0.5 r)))))
         (tall-enough (lambda (h r) (and (> h 50) (> (/ h height) (* 0.5 r)))))
         (big-enough (lambda (w r1 h r2) (and (wide-enough w r1) (tall-enough h r2))))
         (mondrian-helper (lambda (x y rands)
         (if(big-enough x (first rands) y (first rands))
            (begin
            (if (> (first (rest rands)) .5)
                (begin
                (hc-append
                 (if (> (first (rest (rest rands))) .5)
                     (begin
                     (hc-append
                     (vc-append
                      (mondrian-helper (/ x 3) (/ y 3) (rest (rest (rest rands))))
                      (mondrian-helper (/ x 3) (* (/ y 3) 2) (rest (rest (rest rands))))
                      )
                     (vc-append
                      (mondrian-helper (* (/ x 3) 2) (/ y 3) (rest (rest (rest rands))))
                      (mondrian-helper (* (/ x 3) 2) (* (/ y 3) 2) (rest (rest (rest rands))))
                      )
                     )
                     )
                     (begin
                     (hc-append
                     (vc-append
                      (mondrian-helper (* (/ x 3) 2) (* (/ y 3) 2) (rest (rest (rest rands))))
                      (mondrian-helper (* (/ x 3) 2) (/ y 3) (rest (rest (rest rands))))
                     )
                     (vc-append
                      (mondrian-helper (/ x 3) (* (/ y 3) 2) (rest (rest (rest rands))))
                      (mondrian-helper (/ x 3) (/ y 3) (rest (rest (rest rands))))
                     )
                     )
                     )
                  )
                 )
                )
                (hc-append
                 (if (> (first (rest (rest rands))) .5)
                     (begin
                     (hc-append
                     (vc-append
                      (mondrian-helper (/ x 3) (* (/ y 3) 2) (rest (rest (rest rands))))
                      (mondrian-helper (/ x 3) (/ y 3) (rest (rest (rest rands))))
                      )
                     (vc-append
                      (mondrian-helper (* (/ x 3) 2) (* (/ y 3) 2) (rest (rest (rest rands))))
                      (mondrian-helper (* (/ x 3) 2) (/ y 3) (rest (rest (rest rands))))
                      )
                     )
                     )
                     (begin
                     (hc-append
                     (vc-append
                      (mondrian-helper (* (/ x 3) 2) (/ y 3) (rest (rest (rest rands))))
                      (mondrian-helper (* (/ x 3) 2) (* (/ y 3) 2) (rest (rest (rest rands))))
                     )
                     (vc-append
                      (mondrian-helper (/ x 3) (/ y 3) (rest (rest (rest rands))))
                      (mondrian-helper (/ x 3) (* (/ y 3) 2) (rest (rest (rest rands))))
                     )
                     )
                     )
                  )
                 )

            )
            )
            (if (wide-enough x (first rands))
                (begin
                (if (> (first (rest rands)) .5)
                    (begin
                     (hc-append
                      (mondrian-helper (/ x 3) y (rest (rest rands)))
                      (mondrian-helper (* (/ x 3) 2) y (rest (rest rands)))
                      )
                     )
                    (begin
                    (hc-append
                     (mondrian-helper (* (/ x 3) 2) y (rest (rest rands)))
                     (mondrian-helper (/ x 3) y (rest (rest rands)))
                     )
                    )
                )
                )
                (if (tall-enough y (first rands))
                    (begin
                    (if (> (first (rest rands)) .5)
                        (begin
                        (vc-append
                         (mondrian-helper x (/ y 3) (rest (rest rands)))
                         (mondrian-helper x (* (/ y 3) 2) (rest (rest rands)))
                        )
                        )
                        (begin
                        (vc-append
                         (mondrian-helper x (* (/ y 3) 2) (rest (rest rands)))
                         (mondrian-helper x (/ y 3) (rest (rest rands)))
                        )
                        )
                    )
                    )
                    ;;if not big,tall, or wide enough fill in the square
                    ;;a random number is consumed even though the rect is too small
                    (if(> (first rands) .25)
                       (begin
                       (cc-superimpose (colorize (filled-rectangle x y) "white") (rectangle x y))
                       )
                       ;;if it's not white it is either red, yellow, or blue
                       (if (> (first rands) .1667)
                           (begin
                           (cc-superimpose (colorize (filled-rectangle x y) "yellow") (rectangle x y))
                           )
                           (if (> (first rands) .0833)
                               (begin
                               (cc-superimpose (colorize (filled-rectangle x y) "blue") (rectangle x y))
                               )
                               (begin
                               (cc-superimpose (colorize (filled-rectangle x y) "red") (rectangle x y))
                               )
                           )
                       )
                    )
                )
             )
         )))
         )
    (define a (mondrian-helper x y rands))
    (list a rands)
  )
  
)

(randoms 100 100)
(define (save-pict picture filename)
  (send (pict->bitmap picture) save-file filename 'png))

(mondrian 40 40 '(.10))

(mondrian 400 400 (randoms 493545829 40))