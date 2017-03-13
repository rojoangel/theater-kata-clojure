(ns theater.core)

; a theater is represented as a list of rows represented by letters
; (the lower the letter, the closest to the front)
; with the available seats marked with 0,
; and the occupied seats with 1
; e.g. this represents a 5x5 theater with all seats available
; [{:A [0,0,0,0,0]},
;  {:B [0,0,0,0,0]},
;  {:C [0,0,0,0,0]},
;  {:D [0,0,0,0,0]},
;  {:E [0,0,0,0,0]}]

(defn suggest [theater party-size]
  [{:A 1} {:A 2}])
