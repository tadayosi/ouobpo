package org.ouobpo.specium;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class SpecificationSpec extends
        jdave.Specification<Specification<String>> {

    public class SpecOfSpecification {

        private Specification<String> spec;

        public Specification<String> create() {
            spec = new NotEmptyStringSpec();
            return spec;
        }

        public void behavioralSpec1() {
            specify(spec.isSatisfiedBy(null), false);
            specify(spec.isSatisfiedBy(""), false);
            specify(spec.isSatisfiedBy("abc"), true);
        }
    }

    private class NotEmptyStringSpec implements Specification<String> {

        public boolean isSatisfiedBy(String str) {
            return str != null && !("".equals(str));
        }

        public boolean isGeneralizationOf(Specification<String> spec) {
            throw new UnsupportedOperationException();
        }

        public Specification<String> and(Specification<String> spec) {
            throw new UnsupportedOperationException();
        }

        public Specification<String> or(Specification<String> spec) {
            throw new UnsupportedOperationException();
        }

        public Specification<String> remainderUnsatisfiedBy(String str) {
            throw new UnsupportedOperationException();
        }
    }
}
