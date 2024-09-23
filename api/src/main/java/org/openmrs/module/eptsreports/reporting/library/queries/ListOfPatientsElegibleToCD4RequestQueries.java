package org.openmrs.module.eptsreports.reporting.library.queries;

import org.openmrs.module.eptsreports.reporting.utils.EptsQuerysUtils;
import org.springframework.stereotype.Component;

@Component
public class ListOfPatientsElegibleToCD4RequestQueries {

  private static final String ELEGIBLECD4 = "ELEGIBLECD4/CD4DISAGRAGATIONS.sql";

  public static final String findPatientsWhoAreHaveCd4Request(final Criteria criteria) {

    String query = EptsQuerysUtils.loadQuery(ELEGIBLECD4);

    switch (criteria) {
      case C1:
        query =
            query
                + "where final.criteria=1 and saidas.patient_id is null group by final.patient_id  ";
        break;

      case C2:
        query =
            query
                + "where final.criteria=2 and saidas.patient_id is null group by final.patient_id  ";
        break;

      case C3:
        query =
            query
                + "where final.criteria=3 and saidas.patient_id is null group by final.patient_id  ";
        break;

      case C4:
        query =
            query
                + "where final.criteria=4 and saidas.patient_id is null group by final.patient_id  ";
        break;
      case C5:
        query =
            query
                + "where final.criteria=5 and saidas.patient_id is null group by final.patient_id  ";
        break;
      case C6:
        query =
            query
                + "where final.criteria=6 and saidas.patient_id is null group by final.patient_id  ";
        break;

      default:
        break;
    }

    return query;
  }
}
