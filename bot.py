import mechanize
import sys

SIGNUP_URL = "https://github.com/join"
AFTER_SIGNUP_URL = SIGNUP_URL + "/plan"
FORM_ORDER = 1
FORM_FIELD_NAMES = ["login", "email", "password", "password_confirmation"]
FORM_FIELD_FORMAT = "user[{}]"
ACCOUNT_NUM = 10
BASE_ACCOUNT = "kaljugasrb"

def print_same(message):
    sys.stdout.write('\r' + " "*80)
    sys.stdout.write('\r' + message)
    sys.stdout.flush()

def check_url(url, url_target):
    if url != url_target:
        print_same(url)
        print
        print "Failed!"
        return False
    return True

fields = [FORM_FIELD_FORMAT.format(name) for name in FORM_FIELD_NAMES]
USERNAME = BASE_ACCOUNT+'{}'
EMAIL = BASE_ACCOUNT+'{}@'+BASE_ACCOUNT+'.com'
PASSWORD = '{}'+BASE_ACCOUNT
values_tmpt = [USERNAME, EMAIL, PASSWORD, PASSWORD]

cookies = mechanize.CookieJar()
opener = mechanize.build_opener(mechanize.HTTPCookieProcessor(cookies))
mechanize.install_opener(opener)

for i in range(ACCOUNT_NUM):
    print_same("Progress: " + str(i) + ' (' + str(round(float(i)/ACCOUNT_NUM*100, 2)) + '%)')
    
    request = mechanize.Request(SIGNUP_URL)
    response = mechanize.urlopen(request)
    
    if not check_url(response.geturl(), SIGNUP_URL):
        break
    
    forms = mechanize.ParseResponse(response, backwards_compat=False)
    response.close()
    
    form = forms[FORM_ORDER]
    values = [v.format(i) for v in values_tmpt]
    for field, value in zip(fields, values):
        form[field] = value
    request2 = form.click()
    
    try:
        response2 = mechanize.urlopen(request2)
    except mechanize.HTTPError, response2:
        pass
    
    if not check_url(response2.geturl(), AFTER_SIGNUP_URL):
        break

    response2.close()
    cookies.clear()
else:
    print_same("Finished!\n")
