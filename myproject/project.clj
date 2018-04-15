(defproject myproject "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://clojars.org/cascalog/versions/2.1.1"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"]
                 [cascalog/cascalog-core "3.0.0"]
                 ])
:profiles { :dev {:dependencies [[org.apache.hadoop/hadoop-core "1.2.1"]]}}
:jvm-opts ["-Xms768m" "-Xmx768m"]