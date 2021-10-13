package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PetServiceTest {
    @Autowired
    private PetService cut;
    private static boolean dataLoaded = false;

    public Integer demandedPetId;
    public Pet expectedDemandedPet;
    private static Pet firstDemandedPet;
    private static Pet secondedDemandedPet;
    private static Pet thirdDemandedPet;

    public PetServiceTest(Integer demandedPetId, Pet expectedDemandedPet){
        this.demandedPetId = demandedPetId;
        this.expectedDemandedPet = expectedDemandedPet;
    }

    @Before
    public void setUp() {
        if(!dataLoaded) {
            Owner dummyOwner = new Owner();
            firstDemandedPet = new Pet();
            firstDemandedPet.setId(1);
            secondedDemandedPet = new Pet();
            secondedDemandedPet.setId(2);
            thirdDemandedPet = new Pet();
            thirdDemandedPet.setId(3);
            cut.savePet(firstDemandedPet, dummyOwner);
            cut.savePet(secondedDemandedPet, dummyOwner);
            cut.savePet(thirdDemandedPet, dummyOwner);
            dataLoaded = true;
        }
    }

    @Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(new Object[][] {{1, firstDemandedPet},
                {2, secondedDemandedPet},
                {3, thirdDemandedPet}}
        );
    }

    @Test
    public void findPet_providedDemandedPetId_correspondingPetMustBeReturned() {
        // Act
        Pet actualDemandedPet = cut.findPet(demandedPetId);

        // Assert
        assertEquals("Pet and the id are not related!", expectedDemandedPet, actualDemandedPet);
    }
}
