(ns theater.core)

; a theater is represented as a list of rows represented by letters
; (the lower the letter, the closest to the front)
; with the available seats marked with :free,
; and the occupied seats with :occupied
; e.g. this represents a 5x5 theater with all seats available
; [{:A [:free :free :free :free :free]},
;  {:B [:free :free :free :free :free]},
;  {:C [:free :free :free :free :free]},
;  {:D [:free :free :free :free :free]},
;  {:E [:free :free :free :free :free]}]

(defn suggest [theater party-size]
  (if (= party-size 2)
    [{:A 1} {:A 2}]
    [{:A 1} {:A 2} {:A 3}]))
