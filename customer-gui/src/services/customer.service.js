import http from "../http-common";

class CustomerDataService {
  getAll() {
    return http.get("/api/customer/all");
  }

  get(id) {
    return http.get(`/api/customer/${id}`);
  }

  create(data) {
    return http.post("/api/customer", data);
  }

  update(id, data) {
    return http.put(`/api/customer/${id}`, data);
  }

  delete(id) {
    return http.delete(`/api/customer/${id}`);
  }

  findCustomer(queryText) {
    return http.get(`/api/customer/all?queryText=${queryText}`);
  }
}

export default new CustomerDataService();