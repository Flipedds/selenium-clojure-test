(ns com.flipedds.myapp-test
  (:require [clojure.test :refer :all]
            [com.flipedds.myapp :refer :all]
            [com.flipedds.utils.driver-utils :refer :all])
  (:import (org.openqa.selenium By Keys WebDriver)))

(deftest search-clojure-test
  (let [^WebDriver driver (get-driver)]
    (try
      (-> driver
          (go-to "https://google.com")
          (click-in (By/name "q"))
          (fill-field (By/name "q") ["clojure" Keys/ENTER])
          (verify-text (By/xpath "//h3[text()=\"Clojure\"]") "Clojure"))
      (catch Exception e
        (println "Error: " (.getMessage e)))
      (finally
        (-> driver (.quit))))))

(deftest search-java-test
  (let [^WebDriver driver (get-driver)]
    (try
      (-> driver
          (go-to "https://google.com")
          (click-in (By/name "q"))
          (fill-field (By/name "q") ["java" Keys/ENTER])
          (verify-text (By/xpath "(//h3[text()=\"Java | Oracle\"])[1]") "Java | Oracle"))
      (catch Exception e
        (println "Error: " (.getMessage e)))
      (finally
        (-> driver (.quit))))))
