package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class OwnerTest {
    private Owner cut;

    @Before
    public void setup() {
        cut = new Owner();
    }

    @After
    public void tearDown() {
        cut = null;
    }

    @Test
    public void getOrSetAddress_providedAddressWasSet_theProvidedAddressMustBeReturned() {
        // Arrange
        String providedAddress = new String("Address");

        // Act
        cut.setAddress(providedAddress);
        String actualAddress = cut.getAddress();

        // Assert
        assertEquals("Addresses do not match!", providedAddress, actualAddress);
    }

    @Test
    public void getOrSetCity_providedCityWasSet_theProvidedCityMustBeReturned() {
        // Arrange
        String providedCity = new String("City");

        // Act
        cut.setAddress(providedCity);
        String actualCity = cut.getAddress();

        // Assert
        assertEquals("Addresses do not match!", providedCity, actualCity);
    }

    @Test
    public void getOrSetTelephone_providedTelephoneWasSet_theProvidedTelephoneMustBeReturned() {
        // Arrange
        String providedTelephone = new String("City");

        // Act
        cut.setAddress(providedTelephone);
        String actualTelephone = cut.getAddress();

        // Assert
        assertEquals("Addresses do not match!", providedTelephone, actualTelephone);
    }

    @Test
    public void getPetsInternal_providedNotNullPetSet_theProvidedPetSetReturned() {
        // Arrange
        Set<Pet> providedPetSet = new HashSet<>();
        providedPetSet.add(new Pet());

        // Act
        cut.setPetsInternal(providedPetSet);
        Set<Pet> actualPetSet = cut.getPetsInternal();

        // Assert
        assertEquals("Sets do not match!", providedPetSet, actualPetSet);
    }

    @Test
    public void getPetsInternal_providedNullPetSet_anEmptyHashSetReturned() {
        // Arrange
        Set<Pet> providedPetSet = null;
        Set<Pet> expectedPetSet = new HashSet<>();

        // Act
        cut.setPetsInternal(providedPetSet);
        Set<Pet> actualPetSet = cut.getPetsInternal();

        // Assert
        assertEquals("Sets do not match!", expectedPetSet, actualPetSet);
    }

    @Test
    public void addPet_providedANewPet_newPetIsAddedToThePetSet() {
        // Arrange
        Pet providedPet = new Pet();
        Set<Pet> petSet = new HashSet<>();
        cut.setPetsInternal(petSet);

        // Act
        cut.addPet(providedPet);

        // Assert
        assertTrue("providedPet Is not in the PetSet!", (cut.getPetsInternal().size() == 1) && (cut.getPetsInternal().contains(providedPet)));
        assertEquals("petOwners do not match!", cut, providedPet.getOwner());
    }

    @Test
    public void addPet_providedAnOldPet_oldPetIsAddedToThePetSet() {
        // Arrange
        Pet providedPet = new Pet();
        providedPet.setId(1); // Sample Id
        Set<Pet> petSet = new HashSet<>();
        cut.setPetsInternal(petSet);

        // Act
        cut.addPet(providedPet);

        // Assert
        assertEquals("providedPet is in the PetSet!", 0, cut.getPetsInternal().size());
        assertEquals("petOwners do not match!", cut, providedPet.getOwner());
    }

    @Test
    public void removePet_providedPetIsInThePetSet_providedPetMustBeRemovedFromTheSet() {
        // Arrange
        Pet providedPet = new Pet();
        Set<Pet> petSet = new HashSet<>();
        petSet.add(providedPet);
        cut.setPetsInternal(petSet);

        // Act
        cut.removePet(providedPet);

        // Assert
        assertEquals("providedPet is still in the PetSet!", 0, cut.getPetsInternal().size());
    }

    @Test
    public void getPets_providedPetSetInternalWithRandomOrder_sortedListOfThePetSetMustBeReturned() {
        // Arrange
        Set<Pet> petSet = new HashSet<>();
        Pet firstPet = new Pet(); firstPet.setName("Ab"); petSet.add(firstPet);
        Pet secondPet = new Pet(); secondPet.setName("c"); petSet.add(secondPet);
        Pet thirdPet = new Pet(); thirdPet.setName("aa"); petSet.add(thirdPet);
        cut.setPetsInternal(petSet);
        List<Pet> expectedSortedPet = Collections.unmodifiableList(new ArrayList<Pet>(Arrays.asList(thirdPet, firstPet, secondPet)));

        // Act
        List<Pet> actualSortedPet = cut.getPets();

        // Assert
        assertEquals("Expected List and actual list are not the same!", expectedSortedPet, actualSortedPet);
    }

    @DataPoints
    public static boolean[] ignoreNewCases(){
        return new boolean[] {true , false};
    }

    @Theory
    public void getPet_demandedNameIsProvided_theCorrespondingPetMustBeReturned(boolean ignoreNewCase) {
        // Arrange
        Set<Pet> petSet = new HashSet<>();
        Pet firstPet = new Pet(); firstPet.setName("Skippy"); petSet.add(firstPet);
        Pet secondPet = new Pet(); secondPet.setName("SCooby"); petSet.add(secondPet);
        Pet thirdPet = new Pet(); thirdPet.setName("snoop Dog"); petSet.add(thirdPet); thirdPet.setId(1); // old Pet
        cut.setPetsInternal(petSet);

        // Act
        Pet actualDemandedNamePet = cut.getPet("snoop Dog", ignoreNewCase);

        // Assert
        assertEquals("Actual pet`s name and expected pet`s name are not the same!", thirdPet, actualDemandedNamePet);
    }

    @Theory
    public void getPet_providedAndCorrespondingPetIsNotInTheSet_nullMustBeReturned(boolean ignoreNewCase) {
        // Arrange
        Set<Pet> petSet = new HashSet<>();
        Pet firstPet = new Pet();
            firstPet.setName("Skippy");
            firstPet.setId(1); // old Pet
            petSet.add(firstPet);
        Pet secondPet = new Pet();
            secondPet.setName("snoop Dog");
            secondPet.setId(2); // old Pet
            petSet.add(secondPet);
        cut.setPetsInternal(petSet);

        // Act
        Pet actualDemandedNamePet = cut.getPet("SCooby", ignoreNewCase);

        // Assert
        assertNull("A corresponding pet was found!", actualDemandedNamePet);
    }
}
