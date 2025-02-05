package delivery_docket_for_delivery_person_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import for_all_entities_package.EntitiesMySQLAccess;

public class DeliveryDocketForDeliveryPersonRU {

	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private EntitiesMySQLAccess deliveryDocketAccess;

	// Constructor
	public DeliveryDocketForDeliveryPersonRU() throws Exception {

		deliveryDocketAccess = new EntitiesMySQLAccess();

	}

	// ------------------------------ Read by ID ----------------------------- //
	public ResultSet readDeliveryDocketById(int id) {

		try {
			// Get the connection from MySQLAccess
			Connection connection = deliveryDocketAccess.getConnection();

			String query = """
					SELECT  dd.order_date,
							dd.track_number,
							dp.dperson_name,
						    cp.customer_name,
					        cp.postcode,
							dd.delivery_status
					FROM Software_Project_NewsCompany.delivery_docket dd
					JOIN Software_Project_NewsCompany.customer_profile cp ON dd.customer_id = cp.id
					JOIN Software_Project_NewsCompany.delivery_person dp ON dd.dperson_id = dp.id
					WHERE dd.id = ?
					""";

			// Create prepared statement
			preparedStatement = connection.prepareStatement(query);

			// Set the delivery_docket ID parameter
			preparedStatement.setInt(1, id);

			// Execute the query
			resultSet = preparedStatement.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
			resultSet = null;
		}

		return resultSet;
	}

	// ----------------------------- Read All --------------------------- //
	public ResultSet readAllDeliveryDocket() {

		try {
			// Get the connection from MySQLAccess
			Connection connection = deliveryDocketAccess.getConnection();

			String query = """
					SELECT  dd.order_date,
							dd.track_number,
							dp.dperson_name,
						    cp.customer_name,
					        cp.postcode,
							dd.delivery_status
					FROM Software_Project_NewsCompany.delivery_docket dd
					JOIN Software_Project_NewsCompany.customer_profile cp ON dd.customer_id = cp.id
					JOIN Software_Project_NewsCompany.delivery_person dp ON dd.dperson_id = dp.id
					""";
			// Create prepared statement
			preparedStatement = connection.prepareStatement(query);

			// Execute the query
			resultSet = preparedStatement.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
			resultSet = null;
		}

		return resultSet;
	}

	// ---------------------------- Update by ID ------------------------- //
	public boolean updateDeliveryDocketById(int id, DeliveryDocket updateDd) {

		boolean updateSuccessful = true;

		try {
			// Get the connection from MySQLAccess
			Connection connection = deliveryDocketAccess.getConnection();

			String query = """
					UPDATE Software_Project_NewsCompany.delivery_docket
					SET track_number = ?, order_date = ?, customer_id = ?, dperson_id = ?
					WHERE id = ?
					""";

			// Create prepared statement
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, updateDd.getTrackNumber());
			preparedStatement.setString(2, updateDd.getOrderDate());
			preparedStatement.setInt(3, updateDd.getCustomerId());
			preparedStatement.setInt(4, updateDd.getDeliveryPersonId());
			preparedStatement.setInt(5, id);

			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			// Check if the update was successful
			if (rowsAffected == 0) {
				updateSuccessful = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			updateSuccessful = false;
		}

		return updateSuccessful;
	}
}
