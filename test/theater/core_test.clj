(ns theater.core-test
  (:use midje.sweet)
  (:use [theater.core]))

(facts "about suggesting seats"
       (facts "about a single-row theater"
              (fact "on an empty teather when party size equals theater size suggests all seats"
                    (let [theater [[:A [:free :free]]]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :A 2)]
                    (let [theater [[:A [:free :free :free]]]]
                      (suggest theater 3)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :A 3)]
                    (let [theater [[:A [:free :free :free :free]]]]
                      (suggest theater 3)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :A 3) (->Seat :A 4)])
              (fact "when party size equals free seats count seats booked by another customer are not offered"
                    (let [theater [[:A [:occupied :free]]]]
                      (suggest theater 1)) => [(->Seat :A 2)]
                    (let [theater [[:A [:occupied :free :free :occupied]]]]
                      (suggest theater 2)) => [(->Seat :A 2) (->Seat :A 3)]))
       (facts "about a multi-row theater"
              (fact "on an empty teather when party size equals theater size suggests all seats"
                    (let [theater [[:A [:free]]
                                   [:B [:free]]]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :B 1)]
                    (let [theater [[:A [:free :free]]
                                   [:B [:free :free]]]]
                      (suggest theater 4)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :B 1) (->Seat :B 2)])))
