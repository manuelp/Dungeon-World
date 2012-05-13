(ns converter.core-test
  (:use clojure.test
        converter.core))

(deftest vectorize-xml-maps
  (testing "Vectorize an XML element."
    (is (= [:p "Hello"]
           (vectorize {:tag :p :attrs nil :content ["Hello"]})))
    (is (= [:div [:p "Hello"]]
           (vectorize {:tag :div
                       :attrs nil
                       :content [{:tag :p :attrs nil :content ["Hello"]}]})))
    (is (= [:div [:p "Hello"] [:p "World"]]
           (vectorize {:tag :div
                       :attrs nil
                       :content [{:tag :p :attrs nil :content ["Hello"]}
                                 {:tag :p :attrs nil :content ["World"]}]})))
    (is (= [:div "word" [:p "paragraph"]]
           (vectorize {:tag :div
                       :attrs nil
                       :content ["word"
                                 {:tag :p :attrs nil :content ["paragraph"]}]})))
    (is (= [:div {:id "warn" :class "par"} [:p "paragraph"]]
           (vectorize {:tag :div
                       :attrs {:id "warn" :class "par"}
                       :content [{:tag :p
                                  :attrs nil
                                  :content ["paragraph"]}]})))))