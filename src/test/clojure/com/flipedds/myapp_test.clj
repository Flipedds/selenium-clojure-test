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

(defn navegar-para [driver url]
  "-> driver.get(url)"
  (-> driver (.get url))
  driver)

(defn clicar-em [driver elemento]
  "-> elemento.click()"
  (-> (-> driver (.findElement elemento)) (.click))
  driver)

(defn preencher-campo [driver path valor]
  "-> elemento.sendKeys(valor)"
  (-> (-> driver (.findElement path)) (.sendKeys (into-array CharSequence valor)))
  driver)

(defn verificarTexto [driver path texto]
  "-> elemento.getText().isEquals(texto)"
  (-> (-> driver (.findElement path) (.getText) (= texto) (is)))
  driver)

(deftest search-clojure-test
  (let [ ^WebDriver driver (get-driver)]
    (try
      (-> driver
          (navegar-para "https://google.com")
          (clicar-em (By/name "q"))
          (preencher-campo (By/name "q") ["clojure" Keys/ENTER])
          (verificarTexto (By/xpath "//h3[text()=\"Clojure\"]") "Clojure"))
      (finally
        (.quit driver)))))

(deftest search-java-test
  (let [^WebDriver driver (get-driver)]
    (try
      (-> driver
         (navegar-para "https://google.com")
         (clicar-em (By/name "q"))
         (preencher-campo (By/name "q") ["java" Keys/ENTER])
         (verificarTexto (By/xpath "(//h3[text()=\"Java | Oracle\"])[1]") "Java | Oracle"))
      (finally
        (.quit driver)))))
