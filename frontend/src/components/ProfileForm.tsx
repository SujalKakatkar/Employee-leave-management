import React, { type JSX } from 'react'
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import {
    Field,
    FieldDescription,
    FieldGroup,
    FieldLabel,
} from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { Textarea } from './ui/textarea'
import { depts } from '@/schemas/profileSchema'

function ProfileForm():JSX.Element {
  return (
      <Card >
          <CardHeader>
              <CardTitle>Profile Details</CardTitle>
              <CardDescription>
                  Enter your information below 
              </CardDescription>
          </CardHeader>
          <CardContent>
              <form>
                  <FieldGroup>
                      <Field>
                          <FieldLabel htmlFor="name">Full Name</FieldLabel>
                          <Input id="name" type="text" placeholder="John Doe" required />
                      </Field>
                      <Field>
                          <FieldLabel htmlFor="phone">Phone</FieldLabel>
                          <Input
                              id="phone"
                              type="text"
                              maxLength={10}
                              placeholder="+91-9036060606"
                              required
                          />
                          {/* <FieldDescription>
                              We&apos;ll use this to contact you. We will not share your email
                              with anyone else.
                          </FieldDescription> */}
                      </Field>
                      <Field>
                          <FieldLabel htmlFor="address">Address</FieldLabel>
                          <Textarea id="address" placeholder='#401, 5th street, Mumbai' required />
                          {/* <FieldDescription>
                              Must be at least 8 characters long.
                          </FieldDescription> */}
                      </Field>
                      <Field>
                        {/* //todo:  make this department as select menu and store all the depts inside the table */}
                          <FieldLabel htmlFor="department">
                              Department
                          </FieldLabel>
                          <Select >
                              <SelectTrigger className="w-45">
                                  <SelectValue placeholder="Department" />
                              </SelectTrigger>
                              <SelectContent>
                                  <SelectGroup>
                                      {depts.options.map((item) => (
                                          <SelectItem key={item} value={item}>
                                              {item}
                                          </SelectItem>
                                      ))}
                                  </SelectGroup>
                              </SelectContent>
                          </Select>
                          {/* <FieldDescription>Please confirm your password.</FieldDescription> */}
                      </Field>
                      <FieldGroup>
                          <Field>
                              <Button type="submit">Update Details</Button>
                              {/* <Button variant="outline" type="button">
                                  Sign up with Google
                              </Button>
                              <FieldDescription className="px-6 text-center">
                                  Already have an account? <a href="#">Sign in</a>
                              </FieldDescription> */}
                          </Field>
                      </FieldGroup>
                  </FieldGroup>
              </form>
          </CardContent>
      </Card>
  )
}

export default ProfileForm