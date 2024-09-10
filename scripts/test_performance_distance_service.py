import requests
import random
import concurrent.futures

NUMBER_OF_REQUESTS = 100
NUMBER_OF_THREADS = 20
URL = "http://localhost:9000/hospital/nearest"
USERNAME = "admin"
PASSWORD = "adminpassword"

def generate_random_coords():
    """Generate random coordinates for latitude and longitude."""
    lat = random.uniform(-90, 90)
    lng = random.uniform(-180, 180)
    return lat, lng

def send_request(lat, lng):
    """Send a GET request to the specified URL with the given coordinates."""
    final_url = f"{URL}?lat={lat}&lng={lng}"
    try:
        response = requests.get(final_url, auth=requests.auth.HTTPBasicAuth(USERNAME, PASSWORD), timeout=10)
        if response.status_code == 200:
            time_taken = response.elapsed.total_seconds()
            print(f"Request successful: {time_taken} sec")
            return time_taken
        else:
            print(f"Request failed with status code: {response.status_code}")
            return None
    except requests.exceptions.RequestException as e:
        print(f"Request failed: {e}")
        return None

def handle_requests(index):
    """Function to handle individual request in a thread."""
    lat, lng = generate_random_coords()
    return send_request(lat, lng)

def main():
    """Main function to send num_requests requests with random coordinates using threads."""
    total_time = 0.0
    successful_requests = 0

    with concurrent.futures.ThreadPoolExecutor(max_workers=NUMBER_OF_THREADS) as executor:
        futures = [executor.submit(handle_requests, i) for i in range(NUMBER_OF_REQUESTS)]
        
        for future in concurrent.futures.as_completed(futures):
            time_taken = future.result()
            if time_taken is not None:
                total_time += time_taken
                successful_requests += 1

    if successful_requests > 0:
        avg_time = total_time / successful_requests
    else:
        avg_time = 0

    print(f"Total threads: {NUMBER_OF_THREADS}")
    print(f"Total requests: {NUMBER_OF_REQUESTS}")
    print(f"Successful requests: {successful_requests}")
    print(f"Total time taken: {total_time:.2f} seconds")
    print(f"Average time per request: {avg_time:.2f} seconds")

if __name__ == "__main__":
    main()
