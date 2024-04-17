package io.doubleloop;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

public class Experiment {
  public interface ThrowingSupplier<A, E extends Throwable> {
    A get() throws E;
  }

  private final String name;
  private BiConsumer<ExperimentResult, ExperimentResult> publishResult;
  private ThrowingSupplier<List<Employee>, IOException> useSupplier;
  private ThrowingSupplier<List<Employee>, IOException> candidateSupplier;

  public Experiment(String name) {
    this.name = name;
  }

  public Experiment use(ThrowingSupplier<List<Employee>, IOException> useSupplier) {
    this.useSupplier = useSupplier;
    return this;
  }

  public Experiment candidate(ThrowingSupplier<List<Employee>, IOException> candidateSupplier) {
    this.candidateSupplier = candidateSupplier;
    return this;
  }

  public Experiment publishResult(BiConsumer<ExperimentResult, ExperimentResult> publishResult) {
    this.publishResult = publishResult;
    return this;
  }

  public List<Employee> run() throws IOException {
    final var useResult = validateExperiment(useSupplier);
    final var candidateResult = validateExperiment(candidateSupplier);
    if (!useResult.equals(candidateResult)) {
      publishResult.accept(useResult, candidateResult);
    }
    if (useResult.isFailure()) {
      throw useResult.cause();
    }
    return useResult.success();
  }

  private ExperimentResult validateExperiment(ThrowingSupplier<List<Employee>, IOException> supplier) {
    try {
      final var result = supplier.get();
      return new ExperimentResult(result, null);
    } catch (Throwable e) {
      return new ExperimentResult(null, e);
    }
  }

  public static class ExperimentResult {
    public ExperimentResult(List<Employee> success, Throwable failure) {
    }

    public boolean equals(ExperimentResult other) {
      return false;
    }

    public boolean isFailure() {
      return false;
    }

    public IOException cause() {
      return null;
    }

    public List<Employee> success() {
      return null;
    }
  }

}