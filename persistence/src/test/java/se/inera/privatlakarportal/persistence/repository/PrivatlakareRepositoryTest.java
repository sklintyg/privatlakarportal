package se.inera.privatlakarportal.persistence.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.inera.privatlakarportal.persistence.config.PersistenceConfig;
import se.inera.privatlakarportal.persistence.config.PersistenceConfigTest;
import se.inera.privatlakarportal.persistence.model.Befattning;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.util.PrivatelakareTestUtil;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = {PersistenceConfigTest.class,PersistenceConfig.class})
@ActiveProfiles({"dev"})
public class PrivatlakareRepositoryTest  {

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Test
    public void testFindOne() {
        Privatlakare saved = privatlakareRepository.save(PrivatelakareTestUtil.buildPrivatlakare());
        Privatlakare read = privatlakareRepository.findOne(saved.getPersonId());

        assertThat(read.getAgarform(), is(equalTo(saved.getAgarform())));
        assertThat(read.getArbetsplatsKod(), is(equalTo(saved.getArbetsplatsKod())));
        assertThat(read.getEnhetsId(), is(equalTo(saved.getEnhetsId())));
        assertThat(read.getEnhetsNamn(), is(equalTo(saved.getEnhetsNamn())));
        assertThat(read.getEnhetSlutDatum(), is(equalTo(saved.getEnhetSlutDatum())));
        assertThat(read.getEnhetStartdatum(), is(equalTo(saved.getEnhetStartdatum())));
        assertThat(read.getEpost(), is(equalTo(saved.getEpost())));
        assertThat(read.getForskrivarKod(), is(equalTo(saved.getForskrivarKod())));
        assertThat(read.getFullstandigtNamn(), is(equalTo(saved.getFullstandigtNamn())));
        assertThat(read.isGodkandAnvandare(), is(equalTo(saved.isGodkandAnvandare())));
        assertThat(read.getHsaId(), is(equalTo(saved.getHsaId())));
        assertThat(read.getKommun(), is(equalTo(saved.getKommun())));
        assertThat(read.getLan(), is(equalTo(saved.getLan())));
        assertThat(read.getPersonId(), is(equalTo(saved.getPersonId())));
        assertThat(read.getPostadress(), is(equalTo(saved.getPostadress())));
        assertThat(read.getPostnummer(), is(equalTo(saved.getPostnummer())));
        assertThat(read.getPostort(), is(equalTo(saved.getPostort())));
        assertThat(read.getTelefonnummer(), is(equalTo(saved.getTelefonnummer())));
        assertThat(read.getVardgivareId(), is(equalTo(saved.getVardgivareId())));
        assertThat(read.getVardgivareNamn(), is(equalTo(saved.getVardgivareNamn())));
        assertThat(read.getVardgivareSlutdatum(), is(equalTo(saved.getVardgivareSlutdatum())));
        assertThat(read.getVardgivareStartdatum(), is(equalTo(saved.getVardgivareStartdatum())));

        assertThat(read.getBefattningar(), is(equalTo(saved.getBefattningar())));
        assertThat(read.getLegitimeradeYrkesgrupper(), is(equalTo(saved.getLegitimeradeYrkesgrupper())));
        assertThat(read.getSpecialiteter(), is(equalTo(saved.getSpecialiteter())));
        assertThat(read.getVerksamhetstyper(), is(equalTo(saved.getVerksamhetstyper())));
        assertThat(read.getVardformer(), is(equalTo(saved.getVardformer())));
    }
}
