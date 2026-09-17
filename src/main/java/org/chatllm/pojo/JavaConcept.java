package org.chatllm.pojo;

public class JavaConcept {

        private String concept;
        private String language;
        private String level;

        public JavaConcept() {
        }

        public String getConcept() {
            return concept;
        }

        public void setConcept(String concept) {
            this.concept = concept;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return "JavaConcept{" +
                    "concept='" + concept + '\'' +
                    ", language='" + language + '\'' +
                    ", level='" + level + '\'' +
                    '}';
        }
}
