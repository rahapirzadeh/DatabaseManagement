from faker import Faker
import csv
faker=Faker()

def wCSV():
    with open(fileName, 'w')as csvfile:
        fieldnames=['firstname','lastname','prefix','id','address','phonenumber','job','company','workphonenumber','creditCard','creditProvider']
        writer=csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        for i in range(int(numTuples)):
            writer.writerow(
                {
                    'firstname':faker.first_name(),
                    'lastname': faker.last_name(),
                    'prefix':faker.prefix(),
                    'id':faker.random_digit(),
                    'address':faker.address(),
                    'phonenumber':faker.phone_number(),
                    'job':faker.job(),
                    'company':faker.company(),
                    'workphonenumber': faker.phone_number(),
                    'creditCard': faker.credit_card_number(card_type=None),
                    'creditProvider':faker.credit_card_provider(card_type=None)

                }
            )

if __name__=='__main__':
    fileName = raw_input('Enter the name of the file you wish to create : ')
    fileName = '../' + fileName + '.csv'
    numTuples=raw_input('enter number of tuples: ')
    wCSV()

