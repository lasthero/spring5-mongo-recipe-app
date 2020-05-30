package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

  @Autowired
  CategoryReactiveRepository categoryReactiveRepository;

  @Before
  public void setUp() {
    categoryReactiveRepository.deleteAll().block();
  }

  @Test
  public void testSave() throws Exception {
    Category category = new Category();
    category.setDescription("Foo");

    categoryReactiveRepository.save(category).block();

    Long count = categoryReactiveRepository.count().block();

    assertEquals(Long.valueOf(1L), count);
  }

  @Test
  public void testFindByDescription() throws Exception {
    Category category = new Category();
    category.setDescription("Foo");

    categoryReactiveRepository.save(category).block();

    Category fetchedCategory = categoryReactiveRepository.findByDescription("Foo").block();

    assertNotNull(fetchedCategory.getId());
  }

}
