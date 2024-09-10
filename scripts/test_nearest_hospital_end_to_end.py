from selenium import webdriver
from selenium.webdriver.common.by import By
import time

def test_nearest_hospital():
    driver = webdriver.Chrome()
    driver.get("http://localhost:3000")
    time.sleep(2)
    button = driver.find_element(By.TAG_NAME, "button")
    button.click()
    time.sleep(2)
    
    try:
        element = driver.find_element(By.XPATH, "//*[contains(text(), 'General Hospital')]")
        print("Test réussi : 'General Hospital' trouvé dans la page.")
    except Exception as e:
        print("Test échoué : 'General Hospital' non trouvé.")
        print(f"Erreur : {e}")
    
    driver.quit()

if __name__ == "__main__":
    test_nearest_hospital()
