(ns com.flipedds.utils.driver-utils
  (:require [clojure.test :refer :all])
  (:import (io.github.bonigarcia.wdm WebDriverManager)
           (org.openqa.selenium.chrome ChromeDriver ChromeOptions)))


(defn get-driver []
  (let [chrome-options (doto (ChromeOptions.) (.addArguments []))]
    (-> (WebDriverManager/chromedriver) (.setup))
    (ChromeDriver. chrome-options)))

(defn go-to [driver url]
  "-> driver.get(url)"
  (-> driver (.get url))
  driver)

(defn click-in [driver element]
  "-> element.click()"
  (-> (-> driver (.findElement element)) (.click))
  driver)

(defn fill-field [driver path value]
  "-> element.sendKeys(value)"
  (-> (-> driver (.findElement path)) (.sendKeys (into-array CharSequence value)))
  driver)

(defn verify-text [driver path text]
  "-> element.getText().isEquals(text)"
  (-> (-> driver (.findElement path)) (.getText) (= text) (is))
  driver)
