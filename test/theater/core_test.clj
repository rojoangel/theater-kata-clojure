(ns theater.core-test
  (:use midje.sweet)
  (:use [theater.core]))

(facts "about suggesting seats"
       (fact "on an two seats empty teather it suggests these seats"
             (let [theater [{:A [0 0]}]]
               (suggest theater 2)) => [{:A 1} {:A 2}]))
