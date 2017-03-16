(ns theater.core-test
  (:use midje.sweet)
  (:use [theater.core]))

(facts "about suggesting seats"
       (facts "about a single-row theater"
              (fact "on an empty teather when party size equals theater size suggests all seats"
                    (let [theater [(->Row :A [:free :free])]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :A 2)]
                    (let [theater [(->Row :A [:free :free :free])]]
                      (suggest theater 3)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :A 3)]
                    (let [theater [(->Row :A [:free :free :free :free])]]
                      (suggest theater 4)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :A 3) (->Seat :A 4)])
              (fact "when party size equals free seats count seats booked by another customer are not offered"
                    (let [theater [(->Row :A [:occupied :free])]]
                      (suggest theater 1)) => [(->Seat :A 2)]
                    (let [theater [(->Row :A [:occupied :free :free :occupied])]]
                      (suggest theater 2)) => [(->Seat :A 2) (->Seat :A 3)]))
       (facts "about a multi-row theater"
              (fact "on an empty teather when party size equals theater size suggests all seats"
                    (let [theater [(->Row :A [:free])
                                   (->Row :B [:free])]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :B 1)]
                    (let [theater [(->Row :A [:free :free])
                                   (->Row :B [:free :free])]]
                      (suggest theater 4)) => [(->Seat :A 1) (->Seat :A 2) (->Seat :B 1) (->Seat :B 2)])
              (fact "when party size equals free seats count seats booked by another customer are not offered"
                    (let [theater [(->Row :A [:occupied :occupied])
                                   (->Row :B [:occupied :free])]]
                      (suggest theater 1)) => [(->Seat :B 2)]
                    (let [theater [(->Row :A [:occupied :occupied :occupied :occupied])
                                   (->Row :B [:occupied :free :free :occupied])]]
                      (suggest theater 2)) => [(->Seat :B 2) (->Seat :B 3)])
              (fact "when party size greater than free seats suggest nothing"
                    (let [theater [(->Row :A [:occupied :occupied])
                                   (->Row :B [:occupied :free])]]
                      (suggest theater 2)) => nil
                    (let [theater [(->Row :A [:occupied :occupied :occupied :occupied])
                                   (->Row :B [:occupied :free :free :occupied])]]
                      (suggest theater 3)) => nil)
              (fact "when party size smaller than free seats prefers seats nearer the front"
                    (let [theater [(->Row :A [:free :free])
                                   (->Row :B [:free :free])]]
                      (suggest theater 2)) => [(->Seat :A 1) (->Seat :A 2)]
                    (let [theater [(->Row :A [:occupied :occupied])
                                   (->Row :B [:free :free])
                                   (->Row :C [:free :free])]]
                      (suggest theater 2)) => [(->Seat :B 1) (->Seat :B 2)])
              (fact "when party size smaller than free seats prefers seats nearer the middle of a row"
                    (let [theater [(->Row :A [:free :free :free :free])
                                   (->Row :B [:free :free :free :free])]]
                      (suggest theater 2)) => [(->Seat :A 2) (->Seat :A 3)]
                    (let [theater [(->Row :A [:free :occupied :occupied :occupied :free])
                                   (->Row :B [:occupied :free :free :free :occupied])]]
                      (suggest theater 3)) => [(->Seat :B 2) (->Seat :B 3) (->Seat :B 4)]
                    (let [theater [(->Row :A [:free :occupied :free :occupied :free])
                                   (->Row :B [:free :occupied :free :occupied :free])
                                   (->Row :C [:free :occupied :free :occupied :free])]]
                      (suggest theater 3)) => [(->Seat :A 3) (->Seat :B 3) (->Seat :C 3)]
                    (let [theater [(->Row :A [:free :free :occupied :free :free])
                                   (->Row :B [:free :free :occupied :free :free])
                                   (->Row :C [:free :free :occupied :free :free])]]
                      (suggest theater 4)) => [(->Seat :A 2) (->Seat :A 4) (->Seat :B 2) (->Seat :B 4)])))
