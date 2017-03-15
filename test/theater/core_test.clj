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
                      (suggest theater 4)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :A 3) (->Seat :A 4)])
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
                      (suggest theater 4)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :B 1) (->Seat :B 2)])
              (fact "when party size equals free seats count seats booked by another customer are not offered"
                    (let [theater [[:A [:occupied :occupied]]
                                   [:B [:occupied :free]]]]
                      (suggest theater 1)) => [(->Seat :B 2)]
                    (let [theater [[:A [:occupied :occupied :occupied :occupied]]
                                   [:B [:occupied :free :free :occupied]]]]
                      (suggest theater 2)) => [(->Seat :B 2) (->Seat :B 3)])
              (fact "when party size greater than free seats suggest nothing"
                    (let [theater [[:A [:occupied :occupied]]
                                   [:B [:occupied :free]]]]
                      (suggest theater 2)) => nil
                    (let [theater [[:A [:occupied :occupied :occupied :occupied]]
                                   [:B [:occupied :free :free :occupied]]]]
                      (suggest theater 3)) => nil)
              (fact "when party size smaller than free seats prefers seats nearer the front"
                    (let [theater [[:A [:free :free]]
                                   [:B [:free :free]]]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :A 2)]
                    (let [theater [[:A [:occupied :occupied]]
                                   [:B [:free :free]]
                                   [:C [:free :free]]]]
                      (suggest theater 2)) => [(->Seat :B 1) (->Seat :B 2)])
              (fact "when party size smaller than free seats prefers seats nearer the middle of a row"
                    (let [theater [[:A [:free :free :free :free]]
                                   [:B [:free :free :free :free]]]]
                      (suggest theater 2)) => [(->Seat :A 2) (->Seat :A 3)]
                    (let [theater [[:A [:free :occupied :occupied :occupied :free]]
                                   [:B [:occupied :free :free :free :occupied]]]]
                      (suggest theater 3)) => [(->Seat :B 2) (->Seat :B 3) (->Seat :B 4)]
                    (let [theater [[:A [:free :occupied :free :occupied :free]]
                                   [:B [:free :occupied :free :occupied :free]]
                                   [:C [:free :occupied :free :occupied :free]]]]
                      (suggest theater 3)) => [(->Seat :A 3) (->Seat :B 3) (->Seat :C 3)])))
