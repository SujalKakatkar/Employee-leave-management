import ProfileForm from '@/components/ProfileForm'
import React from 'react'

function ProfileCompletePage() {
  return (
      <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
          <div className="w-full max-w-sm">
              <ProfileForm />
          </div>
      </div>
  )
}

export default ProfileCompletePage