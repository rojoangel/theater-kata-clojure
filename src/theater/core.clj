(ns theater.core)

(defrecord Seat [row number])
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

(defn- row->availability [row]
  (second row))

(defn- row->size [row]
  (count (row->availability row)))

(defn- row->seats [row]
  (let [row-size (row->size row)
        seat-nums (range 1 (inc row-size))
        seat-letters (repeat row-size (row->letter row))]
    (map ->Seat seat-letters seat-nums)))

(defn- theater->seats [theater]
  (flatten (map row->seats theater)))

(defn- theater->availabilty [theater]
  (flatten (map row->availability theater)))

(defn suggest [theater party-size]
  (let [theater-seats (theater->seats theater)
        theater-availabilty (theater->availabilty theater)]
    (map first (filter #(= :free (second %)) (map vector theater-seats theater-availabilty)))))
