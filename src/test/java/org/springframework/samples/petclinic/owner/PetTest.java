package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class PetTest {
    Pet cut = new Pet();
    public static int n = 0;
    public static Visit firstVisit = new Visit();
    public static Visit secondVisit = new Visit();
    public static Visit thirdVisit = new Visit();
    public static Visit fourthVisit = new Visit();

    @DataPoints
    public static Visit[] membersVisitSet() {
        firstVisit.setDate(LocalDate.of(2020, Month.OCTOBER, 18 ));
        secondVisit.setDate(LocalDate.of(2021, Month.DECEMBER, 11 ));
        thirdVisit.setDate(LocalDate.of(2021, Month.FEBRUARY, 7 ));
        fourthVisit.setDate(LocalDate.of(2020, Month.OCTOBER, 12 ));
        return new Visit[] {firstVisit, secondVisit, thirdVisit, fourthVisit};
    }

    private boolean checkEachMemberIsDifferentInList(List<Visit> visitList) {
        for (int i = 0; i < visitList.size(); ++i)
            for (int j = i + 1; j < visitList.size(); ++j)
                if(visitList.get(i).getDate().equals(visitList.get(j).getDate()))
                    return false;
        return true;
    }

    @Theory
    public void getVisits_differentPermutationOfVisitsProvided_theSortedListMustBeReturned(Visit firstMember, Visit secondMember, Visit thirdMember, Visit fourthMember) {
        // Assume
        assumeNotNull(firstMember); assumeNotNull(secondMember); assumeNotNull(thirdMember); assumeNotNull(fourthMember);
        assumeTrue(checkEachMemberIsDifferentInList(new ArrayList<Visit>(Arrays.asList(firstMember, secondMember, thirdMember, fourthMember))));

        // Arrange
        Set<Visit> visitSet = new LinkedHashSet<>();
        visitSet.add(firstMember);
        visitSet.add(secondMember);
        visitSet.add(thirdMember);
        visitSet.add(fourthMember);
        cut.setVisitsInternal(visitSet);
        List<Visit> expectedSortedVisits = Collections.unmodifiableList(Arrays.asList(secondVisit, thirdVisit, firstVisit, fourthVisit));

        // Act
        List<Visit> actualVisits = cut.getVisits();
        System.out.println("n is " + ++n);

        // Assert
        assertEquals("This set is not sorted!", expectedSortedVisits, actualVisits);
    }
}
