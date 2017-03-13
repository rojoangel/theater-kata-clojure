(ns theater.core-test
  (:use midje.sweet)
  (:use [theater.core]))

(facts "about suggesting seats"
       (fact "on an empty teather when party size equals theater size suggests all seats"
             (let [theater [{:A [:free :free]}]]
               (suggest theater 2)) => [{:A 1} {:A 2}]
             (let [theater [{:A [:free :free :free]}]]
               (suggest theater 3)) => [{:A 1} {:A 2} {:A 3}]))
