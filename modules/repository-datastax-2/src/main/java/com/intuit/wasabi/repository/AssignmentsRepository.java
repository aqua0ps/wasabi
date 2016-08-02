/*******************************************************************************
 * Copyright 2016 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.wasabi.repository;

import java.util.Date;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
//import com.intuit.wasabi.analyticsobjects.counts.AssignmentCounts;
//import com.intuit.wasabi.analyticsobjects.counts.BucketAssignmentCount;
//import com.intuit.wasabi.analyticsobjects.counts.TotalUsers;
//import com.intuit.wasabi.analyticsobjects.Parameters;
//import com.intuit.wasabi.assignmentobjects.Assignment;
import com.intuit.wasabi.assignmentobjects.User;
import com.intuit.wasabi.experimentobjects.Application;
import com.intuit.wasabi.experimentobjects.Bucket;
//import com.intuit.wasabi.experimentobjects.Bucket;
import com.intuit.wasabi.experimentobjects.Context;
import com.intuit.wasabi.experimentobjects.Experiment;

import com.intuit.wasabi.repository.model.ExperimentUser;
import com.intuit.wasabi.repository.model.UserAssignmentLookup;

/**
 * A prototype of repository using datastax accessor.  The comments may be out of sync
 */
@Accessor
public interface AssignmentsRepository {

    /**
     * Get user assignments
     *
     * @param userID   User ID
     * @param appLabel Application Label
     * @param context  Environment context
     * @return Set of experiments for user
     */
	@Query("select * from experiment_user_index " +
            "where user_id = ? and app_name = ? and context = ?")
	ExperimentUser getUserAssignmentsFullByWrappedIds(User.ID userID, Application.Name appLabel, Context context);

	@Query("select experiment_id from experiment_user_index " +
            "where user_id = ? and app_name = ? and context = ?")
	ResultSet getUserAssignmentsByWrappedIds(User.ID userID, Application.Name appLabel, Context context);

	@Query("select experiment_id from experiment_user_index " +
			  "where user_id = ? and app_name = ? and context = ?")
	Result<ExperimentUser> getUserAssignments(String userID, String appLabel, String context);
	
	/**
     * Assign a user to experiment
     *
     * @param assignment Assignment assignment
     * @param experiment For Experiment experiment
     * @param date       Date of user assignment
     * @return Resulting assignment
     */
//    Assignment assignUser(Assignment assignment, Experiment experiment, Date date);
	 @Query("insert into user_assignment_look_up " +
                    "(experiment_id, user_id, context, created, bucket_label) " +
                    "values (?, ?, ?, ?, ?)")
	 public void assignUserToLookup(Experiment.ID experimentId, User.ID userId, Context context, Date date, String buckeLabel);
	
	 @Query("insert into user_assignment_look_up " +
             "(experiment_id, user_id, context, created) " +
             "values (?, ?, ?, ?)")
	 public void assignUserToLookup(Experiment.ID experimentId, User.ID userId, Context context, Date date);

	 @Query("select * from user_assignment_look_up where user_id = ?")
	 public Result<UserAssignmentLookup> getUserAssignmentLookup(User.ID user_id);
	 
	 
	 /**
     * Get assignments
     *
     * @param userID         User Id
     * @param appLabel       Application Label
     * @param context        Environment context
     * @param allExperiments A table of all Experiments for this application
     * @return Table of assignments
     */
	 // Note the client needs to process add more information to create Table from the last argument
	 // allExperiments ( look at CassandraAssignmentRepository for that logic 
	 @Query("select * from experiment_user_index " +
                "where user_id = ? and app_name = ? and context = ?")
	 Result<ExperimentUser> getAssignments(User.ID userId, Application.Name appLabel, Context context);
	 
//    Table<Experiment.ID, Experiment.Label, String> getAssignments(User.ID userID, Application.Name appLabel,
//                                                                  Context context,
//                                                                  Table<Experiment.ID, Experiment.Label,
//                                                                          Experiment> allExperiments);

    /**
     * Get assignment for experiment and user
     *
     * @param experimentID A Experiment.ID, uuid identifier for Experiment
     * @param userID       User Id
     * @param context      Environment context
     * @return Assignment
     */
	 /** this method internally calls lookup or old method */
	 /*
    Assignment getAssignment(Experiment.ID experimentID, User.ID userID, Context context);
	  */
    /**
     * Delete assignment for experiment, user and application
     *
     * @param experiment        Experiment for which assignment is to be deleted
     * @param userID            UserId for to be deleted assignment
     * @param context           Environment context
     * @param appName           Application name
     * @param currentAssignment Assignment to be deleted
     */
	 // This method calls a bunch of method
//    void deleteAssignment(Experiment experiment, User.ID userID, Context context, Application.Name appName,
//                          Assignment currentAssignment);
      @Query("delete from user_assignment_look_up where experiment_id = ? and user_id = ? and context = ?")
	  public void deleteUserFromUserAssignmentLookUp(Experiment.ID experimentID, User.ID userID, Context context);

    /**
     * Assign user to the old user_assignment table.
     * This will be completely removed once new table user_assignment_lookup starts serving all assignments.
     *
     * @param assignment Assignment object
     * @param date       Date of assignment
     * @return Assignment
     */
//    Assignment assignUserToOld(Assignment assignment, Date date);

    /**
     * Assign user to exports
     *
     * @param assignment Assignment object
     * @param date       Date of assignment
     */
//    void assignUserToExports(Assignment assignment, Date date);

    /**
     * Remove index user to bucket
     *
     * @param userID       UserId
     * @param experimentID A Experiment.ID, uuid identifier for Experiment
     * @param context      Environment context
     * @param bucketLabel  Bucket Label
     */
//    void removeIndexUserToBucket(User.ID userID, Experiment.ID experimentID, Context context, Bucket.Label bucketLabel);

    /**
     * Get assignments as a stream
     *
     * @param experimentID     A Experiment.ID, uuid identifier for Experiment
     * @param context          Environment context
     * @param parameters       Parameters object
     * @param ignoreNullBucket Boolean, ignore null Buckets
     * @return assignment stream
     */
//    StreamingOutput getAssignmentStream(final Experiment.ID experimentID, final Context context, Parameters parameters,
//                                        final Boolean ignoreNullBucket);

    /**
     * Push assignment to staging
     *
     * @param exception Exception
     * @param data      Assignment Data to be pushed to staging
     */
// This is just recording an exception ??      
//    void pushAssignmentToStaging(String exception, String data);

    /**
     * Increments the bucket assignments counter up by 1 if countUp is true
     *
     * @param experiment update bucket assignment count for the Experiment experiment
     * @param assignment update bucket assignment count for the Assignment assignment
     * @param countUp    Increments the bucket assignments counter up by 1 if countUp is true
     *                   Decrement if countUp is false
     */
//    void updateBucketAssignmentCount(Experiment experiment, Assignment assignment, boolean countUp);

    /**
     * Gets the current counts of bucket assignments for the different buckets along with their total
     *
     * @param experiment Experiment for which the bucket assignment counts is needed.
     * @return AssignmentCounts
     */
//    AssignmentCounts getBucketAssignmentCount(Experiment experiment);
      // Following is business logic/aggregation that should be removed to service tier
      /*
    public AssignmentCounts getBucketAssignmentCount(Experiment experiment) {
		// Why is this declared here ??
        AssignmentCounts assignmentCounts;

        List<BucketAssignmentCount> bucketAssignmentCountList = new ArrayList<BucketAssignmentCount>();
        Integer totalAssignments = 0;
        Integer nullAssignments = 0;

        String CQL = "SELECT * FROM bucket_assignment_counts  " +
                "WHERE experiment_id =?";
        try {
            Rows<Experiment.ID, String> rows = driver.getKeyspace()
                    .prepareQuery(keyspace.bucketAssignmentCountsCF())
                    .withCql(CQL)
                    .asPreparedStatement()
                    .withByteBufferValue(experiment.getID(), ExperimentIDSerializer.get())
                    .execute()
                    .getResult()
                    .getRows();

            if (!rows.isEmpty()) {
                for (int i = 0; i < rows.size(); i++) {
                    ColumnList<String> columns = rows.getRowByIndex(i).getColumns();
                    if (columns.getStringValue("bucket_label", null) != null) {
                        Bucket.Label bucketLabel = Bucket.Label.valueOf(columns.getStringValue("bucket_label", null));
                        Long numberOfAssignments = columns.getColumnByName("bucket_assignment_count").getLongValue();
                        totalAssignments = totalAssignments + numberOfAssignments.intValue();
                        // Updates the BucketAssignmentCountList with # of assignments for that bucket
                         
                        // Is this check required ? 
                        if (bucketAssignmentCountList != null) {
                            if (!"NULL".equals(bucketLabel.toString())) {
                                bucketAssignmentCountList.add(new BucketAssignmentCount.Builder()
                                        .withBucket(bucketLabel)
                                        .withCount(numberOfAssignments.intValue())
                                        .build());
                            } else {
                                nullAssignments = numberOfAssignments.intValue();
                                bucketAssignmentCountList.add(new BucketAssignmentCount.Builder()
                                        .withBucket(null)
                                        .withCount(nullAssignments)
                                        .build());
                            }
                        }
                    }
                }

            } else {
                bucketAssignmentCountList.add(new BucketAssignmentCount.Builder()
                        .withBucket(null)
                        .withCount(0)
                        .build());
            }

        } catch (ConnectionException e) {
            throw new RepositoryException("Could not fetch the bucket assignment counts for experiment " + experiment.getID(), e);
        }

        // Updates the assignmentCounts with bucketAssignmentCountList and
        // the total # of bucket assignments for that experiment
        assignmentCounts = new AssignmentCounts.Builder()
                .withBucketAssignmentCount(bucketAssignmentCountList)
                .withExperimentID(experiment.getID())
                .withTotalUsers(new TotalUsers.Builder()
                        .withBucketAssignments((long)totalAssignments - nullAssignments)
                        .withNullAssignments(nullAssignments)
                        .withTotal(totalAssignments)
                        .build()).build();
        return assignmentCounts;
    }
       */

}