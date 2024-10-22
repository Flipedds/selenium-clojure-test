# Selenium clojure Test

#### The project is a Clojure-based application that uses Selenium for browser automation and testing. The application is configured with Gradle and includes dependencies for Clojure, Selenium, and WebDriverManager. The tests involve browser interactions such as navigating to a URL, clicking elements, filling out fields, and verifying text on a webpage.  

### Application Overview

The application is set up with the following Gradle configuration:  
    
    Plugins: Clojurephant for Clojure support, Application plugin, and Shadow plugin for packaging.
    
    Dependencies: Includes Clojure, Selenium, and WebDriverManager libraries.

    Repositories: Maven Central and Clojars for dependency resolution.

### Test Overview

The tests are defined in the myapp_test.clj file and utilize utility functions from driver_utils.clj to interact with the browser. The tests perform the following actions:

    search-clojure-test: Opens Google, searches for "clojure", and verifies the presence of a result with the text "Clojure".
    
    search-java-test: Opens Google, searches for "java", and verifies the presence of a result with the text "Java | Oracle".

### Example Code


#### myapp_test.clj

````clojure
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
````

#### driver_utils.clj

````clojure
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
````