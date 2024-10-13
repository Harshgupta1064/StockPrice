<!DOCTYPE html>
<html lang="en">
  <h1>Stock Price Application</h1>

  <div class="section">
        <h2>Overview</h2>
        <p>The <strong>Stock Price Application</strong> is an Android app that allows users to search for stocks and view real-time information such as the current price, percentage change, and other relevant details. The app is designed with a clean and user-friendly interface, providing a seamless experience for users interested in financial data.</p>
    </div>

  <div class="section">
        <h2>Features</h2>
        <ul>
            <li><strong>Stock Search</strong>: Users can search for stocks by their names or symbols using an intuitive search bar.</li>
            <li><strong>Real-Time Price Updates</strong>: The app fetches real-time stock prices and displays them to the user.</li>
            <li><strong>Price Details</strong>: Users can view detailed information, including:
                <ul>
                    <li>Current stock price</li>
                    <li>Price change percentage</li>
                    <li>Stock symbol and name</li>
                </ul>
            </li>
            <li><strong>Dark and Light Mode Support</strong>: The app automatically adjusts its theme based on the user's device settings, providing a consistent experience in both light and dark modes.</li>
            <li><strong>User-Friendly Interface</strong>: Built with Material Design principles, the app ensures an aesthetically pleasing and functional layout.</li>
        </ul>
    </div>

  <div class="section">
        <h2>Technologies Used</h2>
        <ul>
            <li><strong>Android SDK</strong>: The app is built using the Android SDK.</li>
            <li><strong>Kotlin</strong>: The primary programming language used for app development.</li>
            <li><strong>Retrofit</strong>: A type-safe HTTP client for Android to handle API requests.</li>
            <li><strong>ViewModel</strong>: Part of Android Architecture Components to manage UI-related data lifecycle-consciously.</li>
            <li><strong>MVVM Architecture</strong>: Utilizes the Model-View-ViewModel pattern for a clean separation of concerns, enhancing testability and maintainability.</li>
            <li><strong>LiveData</strong>: Provides a lifecycle-aware observable data holder class to handle UI updates based on data changes.</li>
            <li><strong>Material Design</strong>: For creating a modern and responsive user interface, ensuring a consistent experience across devices.</li>
            <li><strong>Gradle</strong>: Build automation tool used for managing project dependencies and builds.</li>
        </ul>
    </div>

  <div class="section">
        <h2>Getting Started</h2>

  <h3>Prerequisites</h3>
        <p>Ensure you have the following installed:</p>
        <ul>
            <li>Android Studio</li>
            <li>An Android device or emulator for testing</li>
        </ul>

  <h3>Installation</h3>
        <ol>
            <li>Clone the repository:</li>
            <pre><code>git clone https://github.com/Harshgupta1064/StockPrice.git</code></pre>
            <li>Open the project in Android Studio.</li>
            <li>Sync the project with Gradle files.</li>
            <li>Run the app on an Android device or emulator.</li>
        </ol>
    </div>

  <div class="section">
        <h2>APK Download</h2>
        <p>You can download the latest APK file of the Stock Price Application from the link below:</p>
        <ul>
            <li><a href="https://drive.google.com/file/d/1ZlkP-OycHtRMK41ykLrRkaQ5HGGjX5rV/view?usp=sharing" target="_blank">Download APK</a></li>
        </ul>
    </div>

  <div class="section">
        <h2>API</h2>
        <p>The application uses the <a href="https://finnhub.io/" target="_blank">Finnhub API</a> to fetch stock data. You will need to sign up for a free API key to access the stock market data.</p>

  <h3>API Endpoints</h3>
        <ul>
            <li><strong>Get Stock Price:</strong> Fetches the current price and details for a given stock symbol.</li>
        </ul>
    </div>

  <div class="section">
        <h2>Contributing</h2>
        <p>If you'd like to contribute to the project, please follow these steps:</p>
        <ol>
            <li>Fork the repository.</li>
            <li>Create a new branch (git checkout -b feature/YourFeature).</li>
            <li>Make your changes and commit them (git commit -m 'Add some feature').</li>
            <li>Push to the branch (git push origin feature/YourFeature).</li>
            <li>Open a pull request.</li>
        </ol>
    </div>

  <div class="section">
        <h2>License</h2>
        <p>This project is licensed under the MIT License - see the <a href="LICENSE" target="_blank">LICENSE</a> file for details.</p>
    </div>

  <div class="section">
        <h2>Acknowledgments</h2>
        <p>Thank you to <a href="https://finnhub.io/" target="_blank">Finnhub</a> for providing the stock market data API.</p>
        <p>Special thanks to the open-source community for their support and resources.</p>
    </div>

</body>
</html>
