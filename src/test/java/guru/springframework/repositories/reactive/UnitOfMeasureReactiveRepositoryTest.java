package guru.springframework.repositories.reactive;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

  public static final String EACH = "Each";
  @Autowired
  UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

  @Before
  public void setUp() {
    unitOfMeasureReactiveRepository.deleteAll().block();
  }

  @Test
  public void testSaveUom() throws Exception {
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setDescription(EACH);

    unitOfMeasureReactiveRepository.save(uom).block();

    Long count = unitOfMeasureReactiveRepository.count().block();

    assertEquals(Long.valueOf(1L), count);
  }

  @Test
  public void testFindUomByDescription() throws Exception {
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setDescription(EACH);

    unitOfMeasureReactiveRepository.save(uom).block();

    UnitOfMeasure fetchedUom = unitOfMeasureReactiveRepository.findByDescription(EACH).block();

    assertEquals(EACH, fetchedUom.getDescription());
  }

}
