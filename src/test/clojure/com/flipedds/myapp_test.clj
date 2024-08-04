(ns com.flipedds.myapp-test
  (:require [clojure.test :refer :all]
            [com.flipedds.myapp :refer :all])
  (:import (org.openqa.selenium By Keys WebDriver)
           (org.openqa.selenium.chrome ChromeDriver ChromeOptions)))

(defn get-driver []
  (let [chrome-driver-path "resources/drivers/chromedriver.exe"
        _ (System/setProperty "webdriver.chrome.driver" chrome-driver-path)
        chrome-options (doto (ChromeOptions.)
                         (.addArguments []))]
    (ChromeDriver. chrome-options)))

(deftest search-clojure-test
  (let [ ^WebDriver driver (get-driver)]
    (try
      (-> driver (.get "https://google.com"))
      (let [elemento (-> driver (.findElement (By/name "q")))] ; encontrar elemento
        (-> elemento (.click))                              ; clicar no elemento
        (-> elemento (.sendKeys (into-array CharSequence ["clojure" (Keys/ENTER)])))) ; preencher elemento e pressionar ENTER
      (is (= "Clojure" (-> driver (.findElement (By/xpath "//h3[text()=\"Clojure\"]")) (.getText))))
      (finally
        (.quit driver)))))

(deftest search-java-test
  (let [^WebDriver driver (get-driver)]
    (try
      (-> driver (.get "https://google.com"))
      (let [elemento (-> driver (.findElement (By/name "q")))]  ; encontrar elemento
        (-> elemento (.click))                                  ; clicar no elemento
        (-> elemento (.sendKeys (into-array CharSequence ["java" (Keys/ENTER)])))) ; preencher elemento e pressionar ENTER
      (is (= "Java | Oracle" (-> driver (.findElement (By/xpath "(//h3[text()=\"Java | Oracle\"])[1]")) (.getText)))) ; asserção recuperando texto de elemento
      (finally
        (.quit driver)))))
