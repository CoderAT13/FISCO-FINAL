// array in local storage for registered users

let users = JSON.parse(localStorage.getItem('users')) || [];
    
export function configureFakeBackend() {
    let realFetch = window.fetch;
    window.fetch = function (url, opts) {
        return new Promise((resolve, reject) => {
            // wrap in timeout to simulate server api call
            setTimeout(() => {

                // authenticate

                if (url.endsWith('/login') && opts.method === 'POST') {
                    let responseJson = {"status": 2};
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(responseJson))});
                    return;
                }

                if (url.match(/\/user\/register/) && opts.method === 'GET') {
                    let responseJson = {"status": 0};
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(responseJson))});
                    return;
                }

                if (url.match(/\/user\/search/) && opts.method === 'GET') {
                    // get new user object from post body
                    let responseJson = {"status": 0, "amount": 1000}
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(responseJson)) });
                    return;
                }

                if (url.match(/\/user\/transfer/) && opts.method === 'GET') {
                    // get new user object from post body
                    let responseJson = {"status": 0}
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(responseJson)) });
                    return;
                }

                if (url.match(/\/user\/pay/) && opts.method === 'GET') {
                    // get new user object from post body
                    let responseJson = {"status": 0}
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(responseJson)) });
                    return;
                }

                // pass through any requests not handled above
                realFetch(url, opts).then(response => resolve(response));

            }, 500);
        });
    }
}