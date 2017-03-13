(ns theater.core)

; a theater is represented as a list of rows represented by letters
; (the lower the letter, the closest to the front)
; with the available seats marked with :free,
; and the occupied seats with :occupied
; e.g. this represents a 5x5 theater with all seats available
; [[:A [:free :free :free :free :free]],
;  [:B [:free :free :free :free :free]],
;  [:C [:free :free :free :free :free]],
;  [:D [:free :free :free :free :free]],
;  [:E [:free :free :free :free :free]]]

(defn- row->letter [row]
  (first row))

(defn- row->seat-availability [row]
  (second row))

(defn- row->size [row]
  (count (row->seat-availability row)))

(defn- row->seats [row]
  (let [row-size (row->size row)
        seat-nums (range 1 (inc row-size))
        seat-letters (repeat row-size (row->letter row))]
    (map vector seat-letters seat-nums)))

(defn suggest [theater party-size]
  (row->seats (first theater)))
